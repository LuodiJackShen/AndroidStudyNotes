package com.jack.fasthelp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Process;

import com.jack.fasthelp.api.ApiFactory;
import com.jack.fasthelp.api.IUploadBugFileApi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 2017/5/9 17:04.
 * Copyright 2017 Jack
 */

public final class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String PATH =
            Environment.getExternalStorageDirectory().getPath() + "/fh_debug_log";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".txt";

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    public static CrashHandler getInstance() {
        return sInstance;
    }

    private CrashHandler() {
        //nothing here.
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        dumpToSDCard(e);
        e.printStackTrace();
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpToSDCard(Throwable e) {
        long curr = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat")
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(curr));
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String brand = SystemUtil.getDeviceBrand();
        String model = SystemUtil.getSystemModel();
        String systemVersion = SystemUtil.getSystemVersion();
        try {
            File file = new File(PATH, FILE_NAME + time + FILE_NAME_SUFFIX);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println("Brand: " + brand);
            pw.println("Model: " + model);
            pw.println("SystemVersion: " + systemVersion);
            pw.println();
            pw.println("Time: " + time);
            pw.println();
            e.printStackTrace(pw);
            pw.close();
            uploadToServer(file);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void uploadToServer(File file) {
        RequestBody requestBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part
                .createFormData("text", file.getName(), requestBody);
        ApiFactory.getInstance()
                .create(IUploadBugFileApi.class)
                .uploadBugFile(part)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }
}
