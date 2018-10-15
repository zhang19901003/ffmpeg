#include <jni.h>
#include <string>
#include <android/log.h>
#include <stdio.h>
#include <iostream>
#include <fstream>

#define TAG "ffmpeg"
#define LOGD(...)__android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)__android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)__android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)__android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGF(...)__android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__)
#define SRCFILE "foreman_cif.yuv"
#define DSTFILE "out.rgb"

#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <media/NdkMediaCodec.h>
#include <GLES2/gl2.h>
#include <EGL/egl.h>

extern "C" {
#include <libavcodec/avcodec.h>
#include "libavformat/avformat.h"
#include <libavcodec/jni.h>
#include "libswresample/swresample.h"
#include "libswscale/swscale.h"
#include <unistd.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include <unistd.h>
#include "stdio.h"
#include "errno.h"
}

using namespace std;


#define GET_STR(x) #x;
static const char *vertexShader = GET_STR(
        attribute
        vec4 aPosition; //顶点坐标
        attribute
        vec2 aTexCoord; //材质定点坐标
        varying
        vec2 vTexCoord; //输出的材质坐标
        void main() {
            vTexCoord = vec2(aTexCoord.x, 1.0 - aTexCoord.y);
            gl_Position = aPosition;
        }
);
//片元着色器 软解码和部分x86 硬解码
static const char *fragYUV420P = GET_STR(
        precision
        mediump float; //精度
        varying
        vec2 vTexCoord;  //定点着色器传递的坐标
        uniform
        sampler2D yTexture; // 输入的材质 （不透明灰度，单像素）
        uniform
        sampler2D uTexture;
        uniform
        sampler2D vTexture;
        void main() {
            vec3 yuv;
            vec3 rgb;
            yuv.r = texture2D(yTexture, vTexCoord).r;
            yuv.g = texture2D(uTexture, vTexCoord).r - 0.5;
            yuv.b = texture2D(vTexture, vTexCoord).r - 0.5;
            rgb = mat3(1.0, 1.0, 1.0,
                       0.0, -0.39465, 2.03211,
                       1.13983, -0.5806, 0.0) * yuv;
            gl_FragColor = vec4(rgb, 1.0);
        }
);

GLint InitShader(const char *code, EGLint type) {
    //创建 shader
    GLint sh = glCreateShader(type);
    if (sh == 0) {
        LOGW("glCreateShader failed %d", sh);
        return -2;
    }
    //加载shader
    glShaderSource(sh,
                   1,//shader  数量
                   &code, //sharder 代码
                   0); //代码长度
    //编译shader
    glCompileShader(sh);
    //获取编译情况
    GLint status;
    glGetShaderiv(sh, GL_COMPILE_STATUS, &status);
    if (status == 0) {
        LOGW("glCompileShader failed ");
        return -3;
    }
    LOGW("glCompileShader success ");
    return sh;
}

static double r2b(AVRational rational) {
    return rational.num == 0 || rational.den == 0 ? 0 : (double) rational.num /
                                                        (double) rational.den;
}

long long GetNowMs() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    int sec = tv.tv_sec % 36000000;
    long long t = sec * 1000 + tv.tv_usec / 1000;
    return t;
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_adasplus_update_c_XPlay_Open(JNIEnv *env, jobject instance, jstring url_,
                                      jobject surface) {
    const char *url = env->GetStringUTFChars(url_, 0);
    char info[10000] = {0};
    sprintf(info, "%s\n", avcodec_configuration());
    LOGE("%s", avcodec_configuration());

    av_register_all();
    avformat_network_init();
    //注册解码器
    avcodec_register_all();

    AVFormatContext *ic = NULL;

    int re = avformat_open_input(&ic, url, 0, 0);
    if (re != 0) {
        char errorbuf[1024] = {0};
        av_strerror(re, errorbuf, sizeof(errorbuf));
        LOGE("%s", "open failure");
        return 0;
    }
    //flv h264 格式获取不到时长 nb_stream（没有头部信息） 用这个方法去读取一段数据 做探测
    // 但是此方法不能保证所有格式都能获取时长，只能通过遍历帧数获取时长
    re = avformat_find_stream_info(ic, 0);
    if (re != 0) {
        LOGE("%s", "avformat_find_stream_info failure");
    }
    LOGE("%lld", ic->duration);
    LOGE("%d", ic->nb_streams);
    int vS = -1;
    int aS = -1;
    int fps = -1;
    for (int i = 0; i < ic->nb_streams; i++) {

        AVStream *as = ic->streams[i];
        if (as->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            LOGE("视频数据");
            vS = i;
            fps = r2b(as->avg_frame_rate);
            // codec_id 28 h264   AV_PIX_FMT_YUVJ420P = codecpar->format = 8
            LOGW("帧率%d,w=%d,h=%d codeid =%d pixformat =%d", fps, as->codecpar->width,
                 as->codecpar->height,
                 as->codecpar->codec_id,
                 as->codecpar->format);
        } else if (as->codecpar->codec_type == AVMEDIA_TYPE_AUDIO) {
            LOGE("音频数据");
            aS = i;
            LOGW("sample_rate = %d,channels = %d ,sample_format = %d",
                 as->codecpar->sample_rate,
                 as->codecpar->channels,
                 as->codecpar->format);
        }
    }
    aS = av_find_best_stream(ic, AVMEDIA_TYPE_AUDIO, -1, -1, NULL, 0);
    LOGW("av_find_best_stream = %d", aS);
    vS = av_find_best_stream(ic, AVMEDIA_TYPE_VIDEO, -1, -1, NULL, 0);
    LOGW("av_find_best_stream = %d", vS);
    //软解码器
    AVCodec *codec = avcodec_find_decoder(ic->streams[vS]->codecpar->codec_id);
    //硬解码
    // codec = avcodec_find_decoder_by_name("h264_mediacodec");
    if (!codec) {
        LOGW("avcodec_find_decoder null");
        return 0;
    }
    //解码器初始化
    AVCodecContext *vc = avcodec_alloc_context3(codec);
    avcodec_parameters_to_context(vc, ic->streams[vS]->codecpar);
    vc->thread_count = 8;
    //打开解码器
    re = avcodec_open2(vc, 0, 0);
    if (re != 0) {
        LOGW("视频解码器打开失败");
        return 0;
    }
    AVCodecContext *ac = NULL;
    char *pcm = NULL;
    SwrContext *actx = NULL;
    if (aS >= 0) {
        //软解码器
        AVCodec *acodec = avcodec_find_decoder(ic->streams[aS]->codecpar->codec_id);
        ac = avcodec_alloc_context3(acodec);
        avcodec_parameters_to_context(ac, ic->streams[aS]->codecpar);
        //打开解码器
        re = avcodec_open2(ac, 0, 0);
        if (re != 0) {
            LOGW("解码音频器打开失败");
            return 0;
        }
        pcm = new char[48000 * 4 * 2];
        actx = swr_alloc();
        actx = swr_alloc_set_opts(actx, av_get_default_channel_layout(2),
                                  AV_SAMPLE_FMT_S16, ac->sample_rate,
                                  av_get_default_channel_layout(ac->channels),
                                  ac->sample_fmt, ac->sample_rate, 0, 0);
        re = swr_init(actx);
        if (re != 0) {
            LOGW("swr_init failed");
            return 0;
        }
        LOGW("sample_rate1 = %d,channels1 = %d ,sample_format1 = %d",
             ac->sample_rate,
             ac->channels,
             ac->sample_fmt);
    }

    AVPacket *pkt = av_packet_alloc();
    AVFrame *frame = av_frame_alloc();

    long long start = GetNowMs();
    int frameCount = 0;

    ANativeWindow *nwin = ANativeWindow_fromSurface(env, surface);
    //display 创建 初始化
    EGLDisplay display = eglGetDisplay(EGL_DEFAULT_DISPLAY);
    if (display == EGL_NO_DISPLAY) {
        LOGW("eglGetDisplay failed");
        return -1;
    }
    if (EGL_TRUE != eglInitialize(display, 0, 0)) {
        LOGW("eglInitialize failed");
        return -2;
    }
    int width = 1280;
    int height = 720;
    //surface
    //surface 配置  窗口
    EGLConfig config;
    EGLint configNum;
    EGLint configSpec[] = {
            EGL_RED_SIZE, 8,
            EGL_GREEN_SIZE, 8,
            EGL_BLUE_SIZE, 8,
            EGL_SURFACE_TYPE, EGL_WINDOW_BIT, EGL_NONE
    };
    if (EGL_TRUE != eglChooseConfig(display, configSpec, &config, 1, &configNum)) {
        LOGW("eglChooseConfig failed");
        return -3;
    }
    EGLSurface winsurface = eglCreateWindowSurface(display, config, nwin, 0);
    if (winsurface == EGL_NO_SURFACE) {
        LOGW("eglCreateWindowSurface failed");
        return -4;
    }
    //context 创建关联的上下文
    const EGLint ctxAttr[] = {
            EGL_CONTEXT_CLIENT_VERSION, 2, EGL_NONE
    };
    EGLContext context = eglCreateContext(display, config, EGL_NO_CONTEXT, ctxAttr);
    if (context == EGL_NO_CONTEXT) {
        LOGW("eglCreateContext failed");
        return -5;
    }
    if (EGL_TRUE != eglMakeCurrent(display, winsurface, winsurface, context)) {
        LOGW("eglMakeCurrent failed");
        return -6;
    };
    //shader 初始化 顶点，片源
    GLint vsh = InitShader(vertexShader, GL_VERTEX_SHADER);
    GLint fsh = InitShader(fragYUV420P, GL_FRAGMENT_SHADER);
    LOGW("InitShader %d %d", vsh, fsh);
    ///////////////////////////////////////////////////
    //穿件渲染程序
    GLint program = glCreateProgram();
    if (program == 0) {
        LOGD("program failed");
        return -7;
    }
    //渲染程序加入着色器代码
    glAttachShader(program, vsh);
    glAttachShader(program, fsh);
    //链接程序
    glLinkProgram(program);
    GLint status = 0;
    glGetProgramiv(program, GL_LINK_STATUS, &status);
    if (status == 0) {
        LOGD("glLinkProgram failed");
        return -7;
    }
    LOGD("glLinkProgram sucess");
    glUseProgram(program);

    ///////////////////////////////////////
    //加入三维定点数据 两个三角形组成正方形
    static float ver[] = {
            1.0f, -1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f
    };
    GLuint apos = (GLuint) glGetAttribLocation(program, "aPosition");
    glEnableVertexAttribArray(apos);
    glVertexAttribPointer(apos, 3, GL_FLOAT, GL_FALSE, 12, ver);
    //加入材质坐标数据
    static float txts[] = {
            1.0f, 0.0f, //右下
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f
    };
    GLuint atex = (GLuint) glGetAttribLocation(program, "aTexCoord");
    glEnableVertexAttribArray(atex);
    glVertexAttribPointer(atex, 2, GL_FLOAT, GL_FALSE, 8, txts);

    //材质纹理初始化
    //设置纹理层
    glUniform1i(glGetUniformLocation(program, "yTexture"), 0); //对应纹理第一层
    glUniform1i(glGetUniformLocation(program, "uTexture"), 1);
    glUniform1i(glGetUniformLocation(program, "vTexture"), 2);

    //创建opengl 纹理
    GLuint texts[3] = {0};
    //创建三个纹理
    glGenTextures(3, texts);
    //设置纹理属性
    glBindTexture(GL_TEXTURE_2D, texts[0]);
    //缩小过滤器
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexImage2D(GL_TEXTURE_2D, 0,
                 GL_LUMINANCE, //gpu 内部格式 亮度 灰度图
                 width, height,
                 0, //边框
                 GL_LUMINANCE, // 数据的像素格式 亮度 灰度图
                 GL_UNSIGNED_BYTE,//像素的数据类型
                 NULL  //纹理数据
    );

    //设置纹理属性
    glBindTexture(GL_TEXTURE_2D, texts[1]);
    //缩小过滤器
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexImage2D(GL_TEXTURE_2D, 0,
                 GL_LUMINANCE, //gpu 内部格式 亮度 灰度图
                 width / 2, height / 2,
                 0, //边框
                 GL_LUMINANCE, // 数据的像素格式 亮度 灰度图
                 GL_UNSIGNED_BYTE,//像素的数据类型
                 NULL  //纹理数据
    );

    //设置纹理属性
    glBindTexture(GL_TEXTURE_2D, texts[2]);
    //缩小过滤器
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexImage2D(GL_TEXTURE_2D, 0,
                 GL_LUMINANCE, //gpu 内部格式 亮度 灰度图
                 width / 2, height / 2,
                 0, //边框
                 GL_LUMINANCE, // 数据的像素格式 亮度 灰度图
                 GL_UNSIGNED_BYTE,//像素的数据类型
                 NULL  //纹理数据
    );
    //音频采样上下文
    unsigned char *buf[3] = {0};
    buf[0] = new unsigned char[width * height];
    buf[1] = new unsigned char[width * height / 4];
    buf[2] = new unsigned char[width * height / 4];


    for (;;) {
        if (GetNowMs() - start >= 3000) {
            LOGW("now decode fps is  %d", frameCount / 3);
            start = GetNowMs();
            frameCount = 0;
        }
        int re = av_read_frame(ic, pkt);
        if (re != 0) {

            int pos = 20 * r2b(ic->streams[vS]->time_base);
            LOGW("video end %d,%d,%d", ic->streams[vS]->time_base.den,
                 ic->streams[vS]->time_base.num, pos);
            av_seek_frame(ic, vS, pos, AVSEEK_FLAG_BACKWARD | AVSEEK_FLAG_FRAME);
            continue;
        }

        //发送到线程中解码  pkt 会被复制 所以可以清理

        AVCodecContext *cc = vc;

        if (pkt->stream_index != AVMEDIA_TYPE_VIDEO) {
            cc = ac;
        }

        re = avcodec_send_packet(cc, pkt);
        // LOGW("stream = %d,size = %d,pts = %lld, flag =%d",pkt->stream_index,pkt->size,pkt->pts,pkt->flags);
        av_packet_unref(pkt);
        if (re != 0) {
            //解码失败
            LOGW("avcodec_send_packet failure");
            continue;
        }

        for (;;) {
            re = avcodec_receive_frame(cc, frame);
            if (re != 0) {
                //  LOGW("avcodec_receive_frame failure");
                break;
            }
            //        LOGW("avcodec_receive_frame success %lld",frame->pts);
            if (cc == vc) {

                glActiveTexture(GL_TEXTURE0);
                glBindTexture(GL_TEXTURE_2D, texts[0]);
                //替换纹理内容
                glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_LUMINANCE,
                                GL_UNSIGNED_BYTE, frame->buf[0]->data);

                //激活第2层纹理 ,绑定到创建的opengl 纹理
                glActiveTexture(GL_TEXTURE1);
                glBindTexture(GL_TEXTURE_2D, texts[1]);
                //替换纹理内容
                glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width / 2, height / 2, GL_LUMINANCE,
                                GL_UNSIGNED_BYTE, frame->buf[1]->data);

                //激活第3层纹理 ,绑定到创建的opengl 纹理
                glActiveTexture(GL_TEXTURE2);
                glBindTexture(GL_TEXTURE_2D, texts[2]);
                //替换纹理内容
                glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width / 2, height / 2, GL_LUMINANCE,
                                GL_UNSIGNED_BYTE, frame->buf[2]->data);
                //绘制
                glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
                //窗口显示
                eglSwapBuffers(display, winsurface);

                LOGW("%d,%d,%d", frame->buf[0]->data[9999], frame->buf[1]->data[10086],
                     frame->buf[2]->data[18952]);
                frameCount++;

            } else {

                uint8_t *out[2] = {0};
                out[0] = (uint8_t *) pcm;
                int len = swr_convert(actx, out, frame->nb_samples, (const uint8_t **) frame->data,
                                      frame->nb_samples);
                //   LOGW("swr_convert %d", len);
            }
        }
    }

    delete (pcm);
    if (actx != NULL) {
        av_freep(actx);
        actx = NULL;
    }
    avformat_close_input(&ic);
    env->ReleaseStringUTFChars(url_, url);
    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_adasplus_update_c_MainActivity_test(JNIEnv *env, jobject instance) {

//
//    char date[1024 * 4];
//
//    fstream afile;
//    afile.open("/sdcard/gps.txt", ios::out | ios::in | ios::binary);
//    fstream afile1;
//    afile1.open("/sdcard/123456.txt", ios::out | ios::in | ios::trunc);
//    afile.clear();
//    for (;;) {
//        memset(date, 0, strlen(date));
//        if (afile.eof())
//            break;
//        afile.read(date, 1024 * 4);
//        LOGE("%d ,%s ", strlen(date), date);
//        afile1.write(date, strlen(date));
//        memset(date, 0, strlen(date));
//    }
//
//
//    afile.close();
//    afile1.close();

//    char buffer[1000];
//    ifstream in("/sdcard/gps.txt");
//    if (! in.is_open())
//    { cout << "Error opening file"; exit (1); }
//    for (;;)
//    {
//        in.getline (buffer,1000);
//        if(in.eof())
//            break;
//        LOGE("%s , %d", buffer,strlen(buffer));
//
//    }
//    in.close();


    const int in_width = 352;
    const int in_height = 288;

    const int out_width = 640;
    const int out_height = 480;

    const int read_size = in_width * in_height * 3 / 2;
    const int write_size = out_width * out_height * 3 / 2;
    struct SwsContext *img_convert_ctx;
    uint8_t *inbuf[4];
    uint8_t *outbuf[4];
    int inlinesize[4] = {in_width, in_width / 2, in_width / 2, 0};
    int outlinesize[4] = {out_width, out_width / 2, out_width / 2, 0};

    uint8_t in[352 * 288 * 3 >> 1];
    uint8_t out[640 * 480 * 3 >> 1];

    FILE *fin = fopen(SRCFILE, "rb");
    FILE *fout = fopen(DSTFILE, "wb");

    if (fin == NULL) {
        printf("open input file %s error.\n", SRCFILE);
        return;
    }

    if (fout == NULL) {
        printf("open output file %s error.\n", DSTFILE);
        return;
    }

    inbuf[0] = (uint8_t *) malloc(in_width * in_height);
    inbuf[1] = (uint8_t *) malloc(in_width * in_height >> 2);
    inbuf[2] = (uint8_t *) malloc(in_width * in_height >> 2);
    inbuf[3] = NULL;

    outbuf[0] = (uint8_t *) malloc(out_width * out_height);
    outbuf[1] = (uint8_t *) malloc(out_width * out_height >> 2);
    outbuf[2] = (uint8_t *) malloc(out_width * out_height >> 2);
    outbuf[3] = NULL;

    img_convert_ctx = sws_getContext(in_width, in_height, AV_PIX_FMT_YUV420P,
                                     out_width, out_height, AV_PIX_FMT_YUV420P, SWS_POINT,
                                     NULL, NULL, NULL);
    if (img_convert_ctx == NULL) {
        fprintf(stderr, "Cannot initialize the conversion context!\n");
        return;
    }

    fread(in, 1, read_size, fin);

    memcpy(inbuf[0], in, in_width * in_height);
    memcpy(inbuf[1], in + in_width * in_height, in_width * in_height >> 2);
    memcpy(inbuf[2], in + (in_width * in_height * 5 >> 2), in_width * in_height >> 2);

    sws_scale(img_convert_ctx, (const uint8_t *const *) inbuf, inlinesize,
              0, in_height, outbuf, outlinesize);

    memcpy(out, outbuf[0], out_width * out_height);
    memcpy(out + out_width * out_height, outbuf[1], out_width * out_height >> 2);
    memcpy(out + (out_width * out_height * 5 >> 2), outbuf[2], out_width * out_height >> 2);

    fwrite(out, 1, write_size, fout);

    sws_freeContext(img_convert_ctx);

    fclose(fin);
    fclose(fout);

}

extern "C"
JNIEXPORT
jint JNI_OnLoad(JavaVM *vm, void *res) {
    av_jni_set_java_vm(vm, 0);
    return JNI_VERSION_1_4;
}




