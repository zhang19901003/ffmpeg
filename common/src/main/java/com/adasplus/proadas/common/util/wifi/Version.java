package com.adasplus.proadas.common.util.wifi;

import android.os.Build.VERSION;

import java.lang.reflect.Field;

public class Version {

    public final static int SDK = get();

    private static int get() {
        final Class<VERSION> versionClass = VERSION.class;
        try {
            final Field sdkIntField = versionClass.getField("SDK_INT");
            return sdkIntField.getInt(null);
        } catch (NoSuchFieldException e) {
            return Integer.valueOf(VERSION.SDK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
