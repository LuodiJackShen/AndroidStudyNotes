package com.jack.fasthelp.utils;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.jack.fasthelp.app.FhApplication;

/**
 * Created by Jack on 2017/6/14 11:05.
 * Copyright 2017 Jack
 */

public class PermissionUtil {
    public static boolean lackPermissions(String... permissions) {
        for (String p : permissions) {
            if (lackPermission(p)) {
                return true;
            }
        }
        return false;
    }

    public static boolean lackPermission(String p) {
        return ContextCompat.checkSelfPermission(FhApplication.getApplication(), p)
                == PackageManager.PERMISSION_DENIED;
    }
}
