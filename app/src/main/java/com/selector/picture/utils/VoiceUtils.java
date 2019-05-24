package com.selector.picture.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.selector.picture.R;

/**
 * 调用声音的工具类
 * Create by Han on 2019/5/24
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class VoiceUtils {

    private static MediaPlayer mediaPlayer;

    /**
     * 选中时的声音提醒
     *
     * @param mContext Context
     * @param isSound  true有声音  false静音
     */
    public static void playVoice(Context mContext, Boolean isSound) {
        if (mContext == null) return;
        try {
            AssetFileDescriptor file = mContext.getResources().openRawResourceFd(R.raw.music);
            if (file != null) {
                mediaPlayer = new MediaPlayer();
                if (isSound) {
                    mediaPlayer.setVolume(0.5f, 0.5f);
                } else {
                    mediaPlayer.setVolume(0.0f, 0.0f);
                    stop();
                }
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                mediaPlayer.prepareAsync();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer player) {
                        stop();
                    }
                });
            }
        } catch (Exception ioe) {
            stop();
        }
    }

    /**
     * 停止播放
     */
    private static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
