package com.tommy.autocheckin.services;
/**
 * Created by wojia on 2018/6/19.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Date;

/**
 * 在 安卓端只实现 执行一次打卡操作
 * 定时的实现交给 电脑端 python adb 操作
 * 从而解决 安卓 的 休眠带来的 服务被杀打卡失败问题
 */
public class TimerTaskService extends Service {
    public static String TAG = "myinfo";
    public static volatile boolean isChecking = false;
    PowerManager.WakeLock wakeLock = null;
    /*标示service是否已经开启*/

    @Override
    public void onCreate() {
        super.onCreate();
        acquireWakeLock();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseWakeLock();
    }

    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "PostLocationService");
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
    }

    //释放设备电源锁
    private void releaseWakeLock() {
        if (null != wakeLock) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //使用 share preference
                SharedPreferences.Editor editor = getSharedPreferences("usr_data", MODE_PRIVATE).edit();
                //clear all the data
                editor.clear();
                //initialize the flag
                editor.putInt("usr_flag", 0);
                editor.apply();
                //log
                Log.i(TAG, "data put into pref at" + new Date().
                        toString());
            }
        }).start();
        // 开机之后到现在的运行时间(包括睡眠时间)

        //uuid a
        long triggerTimeA = SystemClock.elapsedRealtime();
        //uuid b , 2 min delayed i think its sufficient
        long triggerTimeB = triggerTimeA + 2 * 60 * 1000;


        Intent doCheckIntent1 = new Intent(TimerTaskService.this, doCheckInReceiver.class);
        doCheckIntent1.putExtra("usr_flag", 1);
        PendingIntent sender1 = PendingIntent.getBroadcast(this,
                10086, doCheckIntent1, PendingIntent.FLAG_CANCEL_CURRENT);
        Intent doCheckIntent2 = new Intent(TimerTaskService.this, doCheckInReceiver.class);
        doCheckIntent2.putExtra("usr_flag", 2);
        PendingIntent sender2 = PendingIntent.getBroadcast(this,
                10087, doCheckIntent2, PendingIntent.FLAG_CANCEL_CURRENT);
        //alarm manager
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //  uuid 1
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTimeA, sender1);

        // uuid 2
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTimeB, sender2);

        return super.onStartCommand(intent, flags, startId);
    }


}

