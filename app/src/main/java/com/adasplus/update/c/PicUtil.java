package com.adasplus.update.c;

import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;

import java.io.ByteArrayOutputStream;

/**
 * Created by fengyin on 17-8-28.
 */

public class PicUtil {
    public static byte[] savejpeg(byte[] data, int width, int height) {
        byte[] dataNV21 = new byte[width * height * 3 / 2];
        YV12toNV21(data, dataNV21, width, height);
        YuvImage image = new YuvImage(dataNV21, ImageFormat.NV21, width, height, (int[]) null);
        ByteArrayOutputStream outputSteam = new ByteArrayOutputStream();
        image.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 100, outputSteam);
        return outputSteam.toByteArray();
    }

    private static void YV12toNV21(byte[] input, byte[] output, int width, int height) {
        int frameSize = width * height;
        int qFrameSize = frameSize / 4;
        int tempFrameSize = frameSize * 5 / 4;
        System.arraycopy(input, 0, output, 0, frameSize);

        for (int i = 0; i < qFrameSize; ++i) {
            output[frameSize + i * 2] = input[frameSize + i];
            output[frameSize + i * 2 + 1] = input[tempFrameSize + i];
        }
    }

    public static byte[] YV12Resize(byte[] pSrc, Point szSrc, byte[] pDst, Point szDst) {
        int srcPitchY = szSrc.x, srcPitchUV = szSrc.x / 2, dstPitchY = szDst.x, dstPitchUV = szDst.x / 2;

        int rateX = (szSrc.x << 16) / szDst.x;
        int rateY = (szSrc.y << 16) / szDst.y;
        for (int i = 0; i < szDst.y; i++) {
            int srcY = i * rateY >> 16;

            for (int j = 0; j < szDst.x; j++) {
                int srcX = j * rateX >> 16;
                pDst[dstPitchY * i + j] = pSrc[srcY * srcPitchY + srcX];
            }
        }
        for (int i = 0; i < szDst.y / 2; i++) {
            int srcY = i * rateY >> 16;

            for (int j = 0; j < szDst.x / 2; j++) {
                int srcX = j * rateX >> 16;

                pDst[dstPitchY * szDst.y + i * dstPitchUV + j] = pSrc[srcPitchY * szSrc.y + srcY * srcPitchUV + srcX];//*(pSrcVLine+srcX);
                pDst[dstPitchY * szDst.y + i * dstPitchUV + dstPitchUV * szDst.y / 2 + j] = pSrc[srcPitchY * szSrc.y + srcY * srcPitchUV + srcPitchUV * szSrc.y / 2 + srcX];

            }
        }

        return pDst;
    }

    public  static  byte[] rgb2YCbCr420(byte[] pixels, int width, int height) {
        int len = width * height;
        //yuv格式数组大小，y亮度占len长度，u,v各占len/4长度。
        byte[] yuv = new byte[len * 3 / 2];
        int y, u, v;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //屏蔽ARGB的透明度值
                int rgb = pixels[i * width + j] & 0x00FFFFFF;
                //像素的颜色顺序为bgr，移位运算。
                int r = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb >> 16) & 0xFF;
                //套用公式
                y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
                u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
                v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;
                //调整
                y = y < 16 ? 16 : (y > 255 ? 255 : y);
                u = u < 0 ? 0 : (u > 255 ? 255 : u);
                v = v < 0 ? 0 : (v > 255 ? 255 : v);
                //赋值
                yuv[i * width + j] = (byte) y;
                yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
                yuv[len + +(i >> 1) * width + (j & ~1) + 1] = (byte) v;
            }
        }
        return yuv;

    }

}
