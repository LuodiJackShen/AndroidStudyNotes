package com.jack.fasthelp.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by jack on 18-1-8.
 */

public class VoicePlayerUtil {
    private static final String TAG = "VoicePlayerUtil";

    private volatile static VoicePlayerUtil sInstance;
    private MediaPlayer mPlayer;

    private VoicePlayerUtil() {
    }

    public static VoicePlayerUtil newInstance() {
        if (sInstance == null) {
            synchronized (VoicePlayerUtil.class) {
                if (sInstance == null) {
                    sInstance = new VoicePlayerUtil();
                }
            }
        }
        return sInstance;
    }

    private void reset() {
        stop();
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        } else {
            mPlayer.reset();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void play(String filePath) throws IOException {
        reset();
        mPlayer.setDataSource(filePath);
        mPlayer.prepare();
        mPlayer.start();
    }

    public void stop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    public void destroy() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
