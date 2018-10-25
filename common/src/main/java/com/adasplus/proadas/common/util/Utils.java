package com.adasplus.proadas.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangyapeng on 17-8-18.
 */

public class Utils {

    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
   // public static final String REGEX_CALIB = "-?[0-9]+(\\\\.[0-9]+)?";

    public Utils() {
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String getStrMd5(String msg) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] e = msg.getBytes();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(e);
            byte[] md = digest.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public static String getByteMd5(byte[] array) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(array);
            byte[] md = digest.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }


    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService("wifi");
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        if (TextUtils.isEmpty(mac)) {
            return "";
        } else {
            int length = mac.length();
            if (length >= 15) {
                return mac;
            } else {
                for (int index = 15 - length; index-- > 0; mac = "@" + mac) {
                    ;
                }

                return mac;
            }
        }
    }

    public static String getIMEI(Context context) {
        if ("tengshi".equals("test2")) {
            return "ky0123456789001";
        } else {
            TelephonyManager manager = (TelephonyManager) context.getSystemService("phone");
            String imei = manager.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                return "";
            } else {
                int length = imei.length();
                if (length >= 15) {
                    return imei;
                } else {
                    for (int index = 15 - length; index-- > 0; imei = "@" + imei) {
                        ;
                    }

                    return imei;
                }
            }
        }
    }

    public static String getSN() {
        try {
            Method e = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
            String sn = (String) e.invoke((Object) null, new Object[]{!"tengshi".equals("tianzhiyan") && !"tengshi".equals("dienjie") ? "ro.serialno" : "gsm.serial"});
            sn = sn.trim();
            if (!"tengshi".equals("tianzhiyan") && !"tengshi".equals("dienjie")) {
                return TextUtils.isEmpty(sn) ? "" : sn;
            } else if (TextUtils.isEmpty(sn)) {
                return "";
            } else {
                int length = sn.length();
                if (length >= 15) {
                    return sn;
                } else {
                    for (int index = 15 - length; index-- > 0; sn = "@" + sn) {
                        ;
                    }

                    return sn;
                }
            }
        } catch (Exception var4) {
            var4.getMessage();
            return "";
        }
    }

    public static String getDeviceCode(Context context) {
        return "sn".equals("imei") ? getIMEI(context) : ("sn".equals("mac") ? getMacAddress(context) : ("sn".equals("sn") ? getSN() : ""));
    }

    public static String getFile() {
        try {
            String e = "";
            File file = new File("/mnt/sdcard/EasyConnected/License.ini");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            e = br.readLine();
            br.close();
            fis.close();
            if (TextUtils.isEmpty(e)) {
                return "";
            } else {
                int length = e.length();
                if (length >= 15) {
                    return e;
                } else {
                    for (int index = 15 - length; index-- > 0; e = "@" + e) {
                        ;
                    }

                    return e;
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return "";
        }
    }

    public static String readDeviceId() {
        try {
            Method e = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
            String cid = (String) e.invoke((Object) null, new Object[]{"persist.sys.serialno"});
            if (TextUtils.isEmpty(cid)) {

                return "";
            }

            int length = cid.length();
            if (length >= 15) {
                return cid;
            }

            for (int index = 15 - length; index-- > 0; cid = "@" + cid) {
                ;
            }

            Log.e("Adas", "CID:" + cid);
            return cid;
        } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
        } catch (ClassNotFoundException var5) {
            var5.printStackTrace();
        } catch (InvocationTargetException var6) {
            var6.printStackTrace();
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
        }

        return "";
    }

    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
    public static boolean isCalibrate (String calibrate){
        try {
            BigDecimal bigDecimal = new BigDecimal(calibrate);

            float v = bigDecimal.floatValue();
            if(v<0){
                return false;
            }
            return  true;
        }catch (Exception e){
            Log.e("Adas",e.getMessage());
            return false;
        }


    }
    public static boolean isCar(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern
                .compile("^[京,津,渝,沪,冀,晋,辽,吉,黑,苏,浙,皖,闽,赣,鲁,豫,鄂,湘,粤,琼,川,贵,云,陕,秦,甘,陇,青,台,内蒙古,桂,宁,新,藏,澳,军,海,航,警][A-Z][0-9,A-Z]{5,6}$"); // 验证车牌号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static String getVersion(Context context) {
        try {

            PackageManager manager = context.getPackageManager();
            String packageName = "com.adasplus.proadas.business";
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            String versionName = info.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSystemVersion() {
        return android.os.Build.DISPLAY;
    }

    public static byte[] YV12Resize(byte[] pSrc, Point szSrc, byte[] pDst, Point szDst) {
        int srcPitchY = szSrc.x, srcPitchUV = szSrc.x / 2, dstPitchY = szDst.x, dstPitchUV = szDst.x / 2;

        int rateX = (szSrc.x << 16) / szDst.x;
        int rateY = (szSrc.y << 16) / szDst.y;
        for (int i = 0; i < szDst.y; i++) {
            int srcY = i * rateY >> 16;

            for (int j = 0; j < szDst.x; j++) {
                int srcX = j * rateX >> 16;
                pDst[dstPitchY * i + j] = pSrc[srcY * srcPitchY + srcX];//*(pSrcYLine+srcX);
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
}
