package com.jd.mrd.restaurant.utils;

import android.util.Log;

import com.jd.mrd.mrdframework.core.MrdApplication;
import com.jd.mrd.restaurant.R;

/**
 * by shenmingliang1
 * 2018.07.16 13:46.
 */
public class LogUtil {
    private static boolean IS_DEBUG =
            Boolean.valueOf(MrdApplication.getInstance().getString(R.string.isDebug));

    public static void d(String tag, String msg) {
        if (IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.d(tag, msg, e);
        }
    }

    public static void v(String tag, String msg) {
        if (IS_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.v(tag, msg, e);
        }
    }

    public static void i(String tag, String msg) {
        if (IS_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.i(tag, msg, e);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.e(tag, msg, e);
        }
    }

    public static void w(String tag, String msg) {
        if (IS_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.w(tag, msg, e);
        }
    }
}
