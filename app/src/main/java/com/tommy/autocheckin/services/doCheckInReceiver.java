package com.tommy.autocheckin.services;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import com.tommy.autocheckin.Entity.ContextRunnable;
import com.tommy.autocheckin.utils.ThreadTools;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;
/**
 * Created by wojia on 2018/6/19.
 */
public class doCheckInReceiver extends BroadcastReceiver {
    public static String TAG = "myinfo";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onReceive(Context context, final Intent intent) {
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        List<String> mList = new ArrayList<String>();
//        //点亮解锁交给 python adb 端
//        if (!pm.isInteractive()) {
//            //点亮
//            mList.add("input keyevent 26");
//            wakeAndUnlock(context);11：
//            /*从下往上滑动解锁*/
//            mList.add("input swipe 200 800 200 100");
//        }
//        new ThreadTools(mList).start();
        new Thread(new ContextRunnable(context) {
            @Override
            public void run() {
                //使用 share preference
                SharedPreferences.Editor editor = this.context.getSharedPreferences("usr_data", MODE_PRIVATE).edit();
                //store the data
                if (intent.getIntExtra("usr_flag", 0) == 1) {
                    editor.putInt("usr_flag", 1);
                } else if (intent.getIntExtra("usr_flag", 0) == 2) {
                    editor.putInt("usr_flag", 2);
                }
                editor.apply();
                //log
                Log.d(TAG, "executed in receiver" + new Date().
                        toString());
                List<String> mList = new ArrayList<>();


                if (!isAccessibilitySettingsOn(this.context)) {

                    //打开 accessibility service
                    mList.add("settings put secure enabled_accessibility_services  com.tommy.autocheckin/com.tommy.autocheckin.services.MyAccessibilityService");
                    mList.add("settings put secure accessibility_enabled 1");
                    if (!mList.isEmpty())
                        new ThreadTools(mList).start();
                }
            }


        }).start();
    }


    /**
     * To check if service is enabled
     *
     * @param mContext 5
     * @return 是否开启
     */
    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = "com.tommy.autocheckin/com.tommy.autocheckin.services.MyAccessibilityService";

        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessabilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "服务未启动");
        }

        return false;
    }

    //点亮解锁
    private KeyguardManager.KeyguardLock kl;

//    private void wakeAndUnlock(Context context) {
//        //获取电源管理器对象
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//
//        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
//
//        //点亮屏幕
//        wl.acquire(1000);
//
//        //得到键盘锁管理器对象
//        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//        kl = km.newKeyguardLock("unLock");
//
//        //解锁
//        kl.disableKeyguard();
//        wl.release();
//    }

}
