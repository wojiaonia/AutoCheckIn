package com.tommy.autocheckin.services;
/**
 * Created by wojia on 2018/6/19.
 */

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.tommy.autocheckin.Entity.CheckInInfos;
import com.tommy.autocheckin.utils.SendMessage;
import com.tommy.autocheckin.utils.ShellUtils;
import com.tommy.autocheckin.utils.StringToKeyevent;
import com.tommy.autocheckin.utils.ThreadTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
import static com.tommy.autocheckin.services.TimerTaskService.isChecking;
import static com.tommy.autocheckin.utils.RegexTools.getInfoByRegex;
import static com.tommy.autocheckin.utils.RegexTools.isMatch;


public class MyAccessibilityService extends AccessibilityService {
    public static String TAG = "myinfo";
    public static final String TOYOKE_PACKAGE_NAME = "com.toyokeapp";
    //打卡三步  flags
    public static volatile int flag = -1;

    public static int uuID = 0;
    public static String account1 = "";
    public static String pwd1 = "";
    public static String account2 = "";
    public static String pwd2 = "";
    public static int usr_flag = 0;

    /**
     * 当启动服务的时候就会被调用
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onServiceConnected() {
        isChecking = true;
        //使用 share preference 获取数据，flag参数决定执行打卡的账号对象
        //原因是accessibility service 的 onbind 方法是 final 的不允许修改，因此无法实现传统的 service间通讯
        //另外 由于数据量极小，对阻塞影响不大，采用share pre 实现 TImerTaskService 和 accessibility service 的通讯
        SharedPreferences pref = getSharedPreferences("usr_data", MODE_PRIVATE);
        //初始化账号参数
        account1 = "13632391994";
        pwd1 = "cch13535";
        account2 = "15521027009";
        pwd2 = "1290777847";
        usr_flag = pref.getInt("usr_flag", 0);


        //发送邮件用
        if (usr_flag == 1) {
            uuID = 1;
        } else if (usr_flag == 2) {
            uuID = 2;
        } else {
            Log.i(TAG, "uuid do not find in sharepre");

        }


        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        flag = 0;

        Toast.makeText(this.getApplicationContext(), "服务已经启动", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "服务已经启动");

        List<String> mList = new ArrayList<>();
        //isInteractive 判断屏幕是否点亮，需要api>=21 以上
        if (!pm.isInteractive()) {
            //点亮
            mList.add("input keyevent 26");
            /*从下往上滑动解锁*/
            mList.add("input swipe 200 800 200 100");
        }
        //打开打卡软件
        mList.add("am start -n " + "com.toyokeapp" + "/" + "com.toyokeapp.MainActivity");
        new ThreadTools(mList).start();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        //log
//        Log.d(TAG, "executed in accessibility service thread at " + new Date().
//                toString());
//        try {
//            AccessibilityNodeInfo root = event.getSource();
//            AccessibilityNodeInfo root2 = getRootInActiveWindow();
//            public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
//            public static final int TYPE_APPLICATION = 1;
//            public static final int TYPE_INPUT_METHOD = 2;
//            public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
//            public static final int TYPE_SYSTEM = 3;

//            public static final int TYPE_ANNOUNCEMENT = 16384;
//            public static final int TYPE_ASSIST_READING_CONTEXT = 16777216;
//            public static final int TYPE_GESTURE_DETECTION_END = 524288;
//            public static final int TYPE_GESTURE_DETECTION_START = 262144;
//            public static final int TYPE_NOTIFICATION_STATE_CHANGED = 64;
//            public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024;
//            public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 512;
//            public static final int TYPE_TOUCH_INTERACTION_END = 2097152;
//            public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
//            public static final int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;
//            public static final int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;
//            public static final int TYPE_VIEW_CLICKED = 1;
//            public static final int TYPE_VIEW_CONTEXT_CLICKED = 8388608;
//            public static final int TYPE_VIEW_FOCUSED = 8;
//            public static final int TYPE_VIEW_HOVER_ENTER = 128;
//            public static final int TYPE_VIEW_HOVER_EXIT = 256;
//            public static final int TYPE_VIEW_LONG_CLICKED = 2;
//            public static final int TYPE_VIEW_SCROLLED = 4096;
//            public static final int TYPE_VIEW_SELECTED = 4;
//            public static final int TYPE_VIEW_TEXT_CHANGED = 16;
//            public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;
//            public static final int TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072;
//            public static final int TYPE_WINDOWS_CHANGED = 4194304;
//            public static final int TYPE_WINDOW_CONTENT_CHANGED = 2048;
//            public static final int TYPE_WINDOW_STATE_CHANGED = 32;

//            Log.i(TAG, "event type "+ String.valueOf(event.getEventType()));


//            AccessibilityNodeInfo root2 = getRootInActiveWindow();
//            if (null != root2 && root2.getChildCount()>0 && root2.getChild(0).getChildCount()>0) {
//                for (int i = 0; i < root2.getChild(0).getChildCount(); i++) {
//                    Log.i(TAG, "child" + root2.getChild(0).getChild(i).getClassName().toString() );
//                    if (null!=root2.getChild(0).getChild(i).getText()){
//                        Log.i(TAG, "text" + root2.getChild(0).getChild(i).getText().toString());
//                    }
//                }
//            }
//                if ((null != root2.getChild(0))){
//                    for (int i = 0;i<root2.getChild(0).getChildCount();i++){
//                        Log.i(TAG, "root child child "+root2.getChild(0).getChild(i).toString() + "text" +  root2.getChild(0).getChild(i).getText());
//                        if ((null != root2.getChild(0).getChild(i))){
//                            for (int j = 0;i<root2.getChild(0).getChild(i).getChildCount();i++){
//                                Log.i(TAG, "root child child child"+root2.getChild(0).getChild(i).getChild(j).toString() + "text" +  root2.getChild(0).getChild(i).getChild(j).getText());
//
//                            }
//
//                        }
//                    }
////
//                }
//
//            }
//        }
//        catch (Exception e){
//            Log.i(TAG, e.getLocalizedMessage());
//        }


        try {
            //获取当前app 活跃页面的根页面节点对象
            AccessibilityNodeInfo root = getRootInActiveWindow();
            //根节点和其子节点都是 FrameLayout，FrameLayout 的子节点才是我们需要的各个页面节点，因此直接 root.getChild(0).getChild(i)
            if ((flag == 0) && (event.getEventType() == TYPE_WINDOW_STATE_CHANGED)
                    && (root.getChildCount() > 0) && (root.getChild(0).getChildCount() == 11)) {
                //根据uuId 执行对应账户登录
                if (usr_flag == 1) {
                    doLogin(account1, pwd1);
                } else if (usr_flag == 2) {
                    doLogin(account2, pwd2);
                }

                Log.i(TAG, "login完成");

            }

            //执行打卡操作,获得 打卡信息
            else if ((flag == 1) && event.getEventType() == TYPE_WINDOW_STATE_CHANGED
                    && null != root.getChild(0)
                    && root.getChild(0).getChildCount() == 7
                    && isMatch(".*消息.*", root.getChild(0).getChild(0).getText().toString())) {

                doSignIn();
                Log.i(TAG, "signin完成");

            }

            //执行退出登陆操作，回到登陆页面
            else if (flag == 2 && event.getEventType() == TYPE_WINDOW_CONTENT_CHANGED) {
                doQuit();
                Log.i(TAG, "quit完成");

            }

            if (null != root) {
                root.recycle();
            }
        } catch (Exception e) {
            Log.i(TAG, "出问题拉" + e.getLocalizedMessage());
        }

    }

    @Override
    public void onInterrupt() {
        //We do nothing
    }


//            if (eventClass == "android.app.AlertDialog") {
//                AccessibilityNodeInfo dialog = getRootInActiveWindow();
//                Log.i(TAG, dialog.getText().toString());
//                dialog.recycle();
//            }


    /**
     * 第一步： 登录
     * 页面上 输入账号 adb点击输入accessibility service 无法访问的密码框密码
     * 从而进入到登陆后主页面
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void doLogin(String mAccount, String mPwd) throws InterruptedException {

        //get root window node
        AccessibilityNodeInfo node = getRootInActiveWindow();
        //获得EditText 的父节点
        AccessibilityNodeInfo editTextP = node.getChild(0);

        AccessibilityNodeInfo currentNode = null;

        //若具有子节点就遍历子节点，找到两个EditText
        for (int i = 0; i < editTextP.getChildCount(); i++) {
            currentNode = editTextP.getChild(i);

//            Log.i(TAG, "type is " + currentNode.getClassName() + "    " + currentNode.getText());
            if (currentNode.getClassName().equals("android.widget.EditText")) {
                // 正则模糊匹配，查找账号输入文本编辑框 （edit text） 类型节点
                // 由于密码框做了处理，不能直接 ACTION_SET_TEXT 执行输入密码
                if (currentNode.getText() == null) {
                    try {
                        inputPwdByKeyInput(mPwd);
                    } catch (Exception e) {
                        Log.i(TAG, "密码输入出问题拉！" + e.getLocalizedMessage());
                    }
                    break;
                }
                //正则模糊匹配，查找账号输入文本编辑框 edit text 类型节点
                if (isMatch(".*请输入您的账号.*", currentNode.getText().toString())) {
                    try {
                        inputAccount(currentNode, mAccount);
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        Log.i(TAG, "账号输入出问题拉！" + e.getLocalizedMessage());
                    }
                }

            }


        }
        Thread.sleep(2000);
        //先adb执行返回指令一下，退出输入键盘（遮盖了登陆按钮）
        keyBackAction();
        Thread.sleep(2000);
        //根据坐标位置 adb模拟点击 登陆按钮
        doShellCmdInputTap(540, 1267);
        //注意 recycle 操作应该在 for循环完成后执行
        editTextP.recycle();

        if (currentNode != null) {
            currentNode.recycle();
        }

        //标记 完成第一步：登录
        flag = 1;

    }

    /**
     * 执行退出登录操作
     *
     * @throws InterruptedException 5
     */
    private void doQuit() throws InterruptedException {


        //点击 '退出当前登录' 的按钮
        Thread.sleep(2000);
        doShellCmdInputTap(513, 1733);
        Thread.sleep(1000);

        //点击确定 退出登录
        flag = 3;
        doShellCmdInputTap(871, 1156);


        Thread.sleep(1000);

        //子进程杀死打卡程序进程
        List<String> mListA = new ArrayList<>();
        mListA.add("am force-stop " + TOYOKE_PACKAGE_NAME);
        new ThreadTools(mListA).start();

        List<String> mListB = new ArrayList<>();
        //key_event home button 返回桌面
        mListB.add("input keyevent 3");
        //key_event 熄屏
        mListB.add("input keyevent 26");
        new ThreadTools(mListB).start();
        //新线程执行关闭自身
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> mListB = new ArrayList<>();
                //需要把 app 和 系统的 accessibility service 先后都关闭
                mListB.add("settings put secure enabled_accessibility_services  com.tommy.autocheckin/com.tommy.autocheckin.services.MyAccessibilityService");
                mListB.add("settings put secure accessibility_enabled 0");
                new ThreadTools(mListB).start();
            }
        }).start();

    }

    /**
     * 执行定位打卡操作
     */
    private void doSignIn() throws InterruptedException {

        Thread.sleep(1000);
        //adb 模拟点击 切换到 打卡栏目 ViewGroup
        doShellCmdInputTap(540, 1827);
        Thread.sleep(2000);
        //进入地图页面
        doShellCmdInputTap(135, 390);

        //要足够的时间来避免延迟卡滞
        Thread.sleep(5000);
        //定位确定
        doShellCmdInputTap(1035, 1050);
        Thread.sleep(2000);
        //点击签到
        doShellCmdInputTap(536, 1339);
        Thread.sleep(2000);
        //确定
        doShellCmdInputTap(993, 164);

        //要足够的时间来避免延迟卡滞
        Thread.sleep(8000);

        //获取信息
        //frameLayout node
        AccessibilityNodeInfo root = getRootInActiveWindow().getChild(0);
        if (null != getRootInActiveWindow() && getRootInActiveWindow().getChildCount() > 0) {
            //获取 打卡页面信息
            CheckInInfos infos = getCheckInInfo(root);
            //新线程 发送邮件
            new SendMessage(infos, uuID).start();
        }
        // 在回退前
        flag = 2;

        //回退
        Thread.sleep(4000);
        doShellCmdInputTap(64, 162);

        //点击 主页
        Thread.sleep(1000);
        doShellCmdInputTap(965, 1846);

    }

    /**
     * adb 模拟点击，原因是打卡软件设置了 button_click unaccessible，
     * 也就是说辅助点击会被屏蔽，所以只能从root后的系统级别
     * 进行模拟点击
     *
     * @param x x坐标
     * @param y y坐标
     * @return 5
     */
    private void doShellCmdInputTap(int x, int y) {
        List<String> mCmds = new ArrayList<>();
        mCmds.add("input tap " + x + " " + y);
        ShellUtils.execCmd(mCmds);

    }

    /**
     * 用于adb 输入 密码
     */
    private void inputPwdByKeyInput(String mPwd) {
        //聚焦密码框
        doShellCmdInputTap(441, 1059);
        ShellUtils.execCmd(StringToKeyevent.sToK(mPwd));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void inputAccount(AccessibilityNodeInfo currentNode, String mAccount) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, mAccount);
        currentNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
        currentNode.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);

        Log.i(TAG, "account input");

    }

    /**
     * 执行adb 返回按钮点击操作
     */
    private void keyBackAction() {

        List<String> mCmds = new ArrayList<>();
        mCmds.add("input keyevent 4");

        ShellUtils.execCmd(mCmds);

    }


//    /**
//     * for test
//     * 测试用，列出子节点， 高效一点应该使用 DFS遍历 ，这里忽略，因为Android Studio 自带 hierarchy viewer
//     *
//     * @param root 当前活跃windows 的根节点， 为accessibilityNodeInfo 对象
//     */
//    //Todo 算法搞出来 DFS
//    private void listAllChildNode(AccessibilityNodeInfo root) {
//        for (int i = 0; i < root.getChildCount(); i++) {
//            AccessibilityNodeInfo currentNode = root.getChild(i);
//            Log.i(TAG, "第 " + (i + 1) + " 个节点的 root is " + currentNode.getParent().getClassName().toString());
//            Log.i(TAG, "第 " + (i + 1) + " 个节点的 type is " + currentNode.getClassName().toString());
//            if (currentNode.getText() != null) {
//                Log.i(TAG, "第 " + (i + 1) + " 个节点的 Text is " + currentNode.getText().toString());
//            }
//            currentNode.recycle();
//        }
//    }

    /**
     * 在打卡页面获取 关键信息
     *
     * @return 返回 信息对象
     */
    private CheckInInfos getCheckInInfo(AccessibilityNodeInfo root) {
        CheckInInfos info = new CheckInInfos();

        AccessibilityNodeInfo currentNode = null;
        //遍历
        for (int i = 0; i < root.getChildCount(); i++) {
            currentNode = root.getChild(i);
            if (null != currentNode.getText()) {
                String currentText = currentNode.getText().toString();
                try {
                    Calendar calendar = Calendar.getInstance();
                    info.setDate(calendar);
                    //获取 星期几 常量
                    info.setWeekday(Calendar.DAY_OF_WEEK);


                    //模糊匹配 打卡次数 1 还是 0
                    ArrayList<String> tResult = getInfoByRegex("今日你已签到(\\d)次", currentText);
                    if (null != tResult) {
                        int times = Integer.parseInt(tResult.get(0));
                        info.setCheckInTimes(times);
                    }


//                    //模糊匹配 打卡地点
//                    ArrayList<String> lResult = getInfoByRegex(".*(广东省.*)", currentText);
//                    if (null != lResult) {
//                        String location = lResult.get(0);
//                        info.setLocation(location);
//                    }

                } catch (Exception e) {
                    Log.i(TAG, e.getLocalizedMessage());
                }
            }
        }
        if (null != currentNode) {
            currentNode.recycle();
        }
        Log.i(TAG, String.valueOf(info.getCheckInTimes()) + info.getDate() + info.getWeekday());
        return info;
    }

    /**
     * @param intent 就是关闭此service 的发起者，在这里就是自己（doQuit() 自杀）
     * @return 这里不需要用到
     */
    @Override
    public boolean onUnbind(Intent intent) {

        Log.i(TAG, "accessibility service 关闭！！!!!");
        //Return true if you would like to have the service's onRebind(Intent)
        // method later called when new clients bind to it.
        isChecking = false;
        return false;
    }

//    /**
//     * 网络肯定是通的 ，此方法废弃
//     *
//     * @param context context
//     * @return whether the network is connected
//     */
//    public boolean isNetworkConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//            if (mNetworkInfo != null) {
//                return mNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
}


