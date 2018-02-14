package com.jack.fasthelp.utils;

import android.media.MediaRecorder;

import java.io.IOException;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by jack on 18-1-8.
 */

public class VoiceRecordUtil {
    private volatile static VoiceRecordUtil sInstance;
    private MediaRecorder mRecorder;

    public static VoiceRecordUtil newInstance() {
        if (sInstance == null) {
            synchronized (VoiceRecordUtil.class) {
                if (sInstance == null) {
                    sInstance = new VoiceRecordUtil();
                }
            }
        }
        return sInstance;
    }

    private VoiceRecordUtil() {
    }

    private void reset() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        } else {
            mRecorder.reset();
        }
        // 设置录音的声音来源
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置录制的声音的输出格式（必须在设置声音编码格式之前设置）
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        // 设置声音编码的格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    public void start(String outPath) throws IOException {
        reset();
        mRecorder.setOutputFile(outPath);
        mRecorder.prepare();
        mRecorder.start();
    }

    public void stop() {
        Observable.just(0)
                .subscribeOn(Schedulers.io())
                .subscribe(integer -> {
                    try {
                        Thread.sleep(500);
                        if (mRecorder != null) {
                            mRecorder.stop();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void destroy() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

}
