package com.adasplus.proadas.common;

/**
 * Created by zhangyapeng on 18-3-20.
 */

public class Constants {
    public static final String CALIBRATION = "calibration";
    public static final String INIT = "init";
    public static final String ADASPLUS = "adasplus";
    public static final String SSID = "ssid";
    public static final String CAPABILITIES = "capabilities";
    public static final String BSSID = "bssid";
    public static final String TYPE = "type";
    public static final int CONNECT_TIME = 10000;
    public static boolean sRIGHT_CONNECT = false;
    public static String sCurrentSSID;
    public static final String MAINACTIVITY = "com.adasplus.proadas.business.main.view.activity.MainActivity";
    public static final String HELP = "help";
    public static final String ADAS_SERVICE = "http://192.168.43.1:8000/";
    public static final String HTML_PATH = "path";
    public static long sCurrentDisconnectTime = 0;
    public static long sCurrentConnectTime = 0;
    public static class ServiceConstant {
        public static final String DEVICE_STATE = "deviceState";
        public static final String ACTICATION = "getActivation";
        public static final String DEVICE_INFO = "deviceInfo";
        public static final String WARNPARAMS = "getWarnParams";
        public static final String VOICE_SETTING = "getVoiceSetting";
        public static final String SDCARD_INFO = "sdcardInfo";
        public static final String ADAS_RUNNING_INFO = "adasRuningInfo";
        public static final String ACTIVATEDEVICE = "activateDevice";
        public static final String FORMAT_SDCARD = "formatSdcard";
        public static final String CALIBRATE_PARAMS = "getCalibrateParams";
        public static final String GET_POINT = "getPoint";
        /**
         * updateDeviceInfo
         */
        public static final String UPDATE_DEVICEINFO = "updateDeviceInfo";
        public static final String SET_VOICE = "setVoice";
        public static final String SET_WARNPARAMS = "setWarnParams";
        public static final String SET_RESUME_FACTORY = "resumeFactory";
        public static final String SET_GPS_ENABLE = "setGpsEnable";
        public static final String RESUME_WARNPARMS = "resumeWarnParams";
        public static final String START_PRIVIEW = "startPreview";
        public static final String STOPT_PRIVIEW = "stopPreview";
        public static final String SET_CALIBRATEPARAMS = "setCalibrateParams";
        public static final String SET_HAND_CALIBRATEPARAMS = "setHandCalibrateParams";
        public static final String SET_AUTO_CALIBRATEPARAMS = "setAutoCalibrateParams";
        public static final String PLAY_SOUND = "playsound";
        public static final String GET_TEST_STATE = "getTestState";
        public static final String SET_TEST_STATE = "setTestState";
    }

    public static final String ACTION_GET_DATA_USAGE = "com.rmt.action.GET_DATA_USAGE";
    public static final String ACTION_RECEIVE_DATA_USAGE = "com.rmt.action.RECEIVE_DATA_USAGE";
    public static final String ACTION_RIGHT_OPEN = "com.rmt.android.RIGHT_OPEN";
    public static final String ACTION_RIGHT_CLOSE = "com.rmt.android.RIGHT_CLOSE";
    public static final String ACTION_LEFT_OPEN = "com.rmt.android.LEFT_OPEN";
    public static final String ACTION_LEFT_CLOSE = "com.rmt.android.LEFT_CLOSE";
    public static final String ACTION_MOTION_CRASH = "com.adasplus.motion.crash";
    public static final String ACTION_LOCATION_CLOSE = "com.adasplus.action.location.close";
    public static final String ACTION_LOCATION_OPEN = "com.adasplus.action.location.open";
    public static final String ACTION_WIPE_DATA = "com.adasplus.action.wipedata";
    public static final String ACTION_FORMAT_SDCARD = "com.adasplus.action.formatsdcard";
    public static final int BACK_PREVIEW_WIDTH = 320;
    public static final int BACK_PREVIEW_HEIGHT = 240;
    public static final String EXTRA_START = "start";
    public static final String EXTRA_END = "end";
    public static final String EXTRA_BYTES = "bytes";
    public static final String EXTRA_PHRASE = "Phrase";
    public static final String EXTRA_MSG = "message";

    public static final String ACTION_ACCON = "com.rmt.android.ACC_ON";
    public static final String ACTION_ACCOFF = "com.rmt.android.ACC_OFF";

    public static final String EMERGENCY = "emergency";
    public static final String CARNUM = "carnum";
    public static final int FRONT_WIDTH = 960;
    public static final int FRONT_HEIGHT = 540;
    public static final int FRONT_BUF_LENGTH = FRONT_WIDTH * FRONT_HEIGHT * 3 / 2;
    public static final int FRONT_DATA_LENGTH = 632;

    public static final int BACK_WIDTH = 1280;
    public static final int BACK_HEIGHT = 960;
    public static final int BACK_BUF_LENGTH = BACK_WIDTH * BACK_HEIGHT * 3 / 2;
    public static final int BACK_DATA_LENGTH = 420;

    public static final String CONFIG_NAME = "config";
    public static final String CONFIG_MERCHANTID = "merchantId";
    public static final String CONFIG_X = "configX";
    public static final String CONFIG_Y = "configY";
    public static final String CONFIG_DISTANCE = "distance";
    public static final String CONFIG_CALLPHONE = "callphone";
    public static final String CONFIG_SMOKING = "smoking";
    public static final String CONFIG_LAMP = "lamp";
    public static final String CONFIG_YAWN = "yawn";

    public static final String STATE_ONLINE = "1";
    public static final String STATE_OFFLINE = "0";

    public static final String SOUNDCLASSES = "soundClasses";
    public static final long TOTALSPACE = 1024 * 1024 * 500;

    public static final class AdasPackage {
        public static final String PACKAGE_DISPLAY = "com.adasplus.display";
        public static final String ACTIVITY_DISPLAY = "com.adasplus.display.activity.MainActivity";

        public static final String PACKAGE_FILEMANAGER = "com.mediatek.filemanager";
        public static final String ACTIVITY_FILEMANAGER = "com.mediatek.filemanager.FileManagerOperationActivity";

        public static final String PACKAGE_SETTING = "com.adasplus.setting";
        public static final String ACTIVITY_SETTING = "com.adasplus.setting.activity.MainActivity";

        public static final String PACKAGE_REGISTER = "com.adasplus.setting";
        public static final String ACTIVITIY_REGISTER = "com.adasplus.setting.activity.RegisterActivity";
    }

    public static final String STR_ADAS_RUNNING = "runInfo";
    public static final String STR_STATE_GPS = "GpsState";
    public static final String STR_STATE_SIM = "SimState";
    public static final String STR_STATE_DEVICE = "DeviceState";
    public static final String STR_MERCHANTID = "merchantId";
    public static final String STR_UUID = "uuid";
    public static final String STR_ISACTIVATE = "isActivate";
    public static final String STR_PHONENUMBER = "phoneNum";
    public static final String STR_CARNUM = "carnum";
    public static final String STR_IMEI = "imei";

    public static final String TRUE = "true";
    public static final String CONFIG_TIME = "Systime";
    public static final String CONFIG_VERSION = "Configversion";
    public static final String CONFIG_CONFIG = "AdasConfig";
    public static final String CONFIG_FCWENABLE = "FcwEnable"; //a
    public static final String CONFIG_FCWSENSITIVITY = "FcwSensitivity"; //b
    public static final String CONFIG_FCWSPEED = "FcwSpeed";//c
    public static final String CONFIG_LDWENABLE = "LdwEnable";//d
    public static final String CONFIG_LDWSENSITIVITY = "LdwSensitivity";//e
    public static final String CONFIG_LDWSPEED = "LdwSpeed";//f
    public static final String CONFIG_DFWENABLE = "DfwEnable";//g
    public static final String CONFIG_DFWSENSITIVITY = "DfwSensitivity";//h
    public static final String CONFIG_DFWSPEED = "DfwSpeed";//i
    public static final String CONFIG_PCWENABLE = "PcwEnable";//j
    public static final String CONFIG_PCWSENSITIVITY = "PcwSensitivity";//k
    public static final String CONFIG_PCWSPEED = "PcwSpeed";//l
    public static final String CONFIG_SMOKEENABLE = "SmokeEnable";//m
    public static final String CONFIG_CALLPHONEENABLE = "CallphoneEnable";//n
    public static final String CONFIG_YAWNENABLE = "YawnEnable";//o
    public static final String CONFIG_VOICETYPE = "VoiceType";//p
    public static final String CONFIG_VOICESIZE = "VoiceSize";//q
    public static final String CONFIG_IMGCOUNT = "ImgCount";//r
    public static final String CONFIG_VIDEOCOUNT = "VideoCount";//s
    public static final String CONFIG_UPLOADSENSITIVITY = "UploadSensitivity";//t
    public static final String CONFIG_SERIALPORT_ENABLE = "SerialPortEnable";//u
    public static final String CONFIG_CANENABLE = "CanEnable";//v
    public static final String CONFIG_DRIVEACTION = "DriveAction";//w
    public static final String CONFIG_KAFKAADDRESS = "KafkaAddress";//x
    public static final String CONFIG_BUSINESSID = "BusinessId";//y
    public static final String ENDPOINT = "http://fatigue.oss-cn-beijing.aliyuncs.com";
    public static final String GPS_ENABLE = "enable";
    public static final String ERROR_CODE_FILE_ERROR = "1001";
    public static final String ERROR_CODE_FILE_NOT_EXIST = "1002";
    public static final String CAMERA_ID = "cameraId";
    public static final String CONFIG_PLATENUM = "plateNum";

    public static final String STR_ENDPOINT = "endpoint";
    public static final String STR_BUCKETNAME = "bucketname";
    public static final String STR_FILENAME = "filename";
    public static final String STR_JPEG = ".jpeg";
    public static final String BUCKETNAME = "fatigue";
    public static final String STR_RET = "ret";
    public static final String STR_VERSION = "version";
    public static final String STR_DRIVER_VERSION = "driverVersion";
    public static final String STR_OS_VERSION = "osVersion";
    public static final String STR_APK_VERSION = "apkVersion";

    public static final String CODE_SUCCESS = "1000";
    public static final String CODE_FAIL = "1001";
    public static final String CODE_NO_NETWORK = "1002";
    public static final String CODE_SERVER_ERROR = "1003";
    public static final String CODE_DEVICE_NO_ACTIVATE = "1004";
    public static final String CODE_NO_SDCARD = "1005";
    public static final String CODE_NETWORK_ERROR = "1006";
    public static final int DEFAULT_SERVER_PORT = 8890;
    public static final int DEFAULT_UDP_PORT = 8090;
    public static final String IP = "192.168.43.1";
    public static  final String CAMERAHEIGHT = "cameraHeight";
    public static  final String BUMPERDIS = "bumperDis";
    public static  final String LEFTWHEEL = "leftWheel";
    public static  final String RIGHTWHEEL = "rightWheel";
    public static  final String CALIBRATIONDIS = "calibrationDis";
    public static  final String POINT_X = "pointX";
    public static  final String POINT_Y = "pointY";
}
