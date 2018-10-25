package com.adasplus.proadas.common.view.activity;

/**
 * Created by zhangyapeng on 18-3-26.
 */

import java.util.List;

public interface PermissionListener {
    void onDenied(List<String> list);

    void onGranted();
}