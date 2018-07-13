package com.tommy.autocheckin.utils;

import android.util.Log;

import com.tommy.autocheckin.Entity.CheckInInfos;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Date;

/**
 * Created by wojia on 2018/6/25.
 */

//与网络连接相关的操作都要在子线程中完成，当然也可以在Service服务里操作
public class SendMessage extends Thread {

    private CheckInInfos checkInInfos = null;
    private int uuID = 0;

    public SendMessage(CheckInInfos info, int uuID) {
        this.checkInInfos = info;
        this.uuID = uuID;

    }

    @Override
    public void run() {
        String s = "";
        super.run();
        try {
            if (null != checkInInfos) {
                //创建HtmlEmail类
                SimpleEmail email = new SimpleEmail();
                //填写邮件的主机明，我这里使用的是163
                email.setHostName("smtp.163.com");
                email.setSSL(true);
                //不知为何  我用 465 不行 994 没有问题
                email.setSmtpPort(994);
                //设置字符编码格式，防止中文乱码
                email.setCharset("utf-8");

                //设置发件人的邮箱
                email.setFrom("dakaxiaozhushou@163.com");
                //填写发件人的用户名和密码
                email.setAuthentication("dakaxiaozhushou@163.com", "Cch13535");

                //填写邮件内容
//                String checkLocation = checkInInfos.getLocation();
                String weekday = checkInInfos.getWeekday();
                //硬生生的转
                String checkTime = String.valueOf(checkInInfos.getDate().getTime().getHours()) + ":" +
                        String.valueOf(checkInInfos.getDate().getTime().getMinutes()) + ":" +
                        String.valueOf(checkInInfos.getDate().getTime().getSeconds()) + " " +
                        weekday;
                //设置收件人的邮箱
                switch (uuID) {
                    case 1:
                        email.addTo("953633450@qq.com");
                        email.setSubject("陈灿辉同学，今天成功打卡了哦! " + "打卡时间为：" + checkTime);
                        break;
                    case 2:
                        email.addTo("1290777847@qq.com");
                        email.setSubject("王韩钠同学，今天成功打卡了哦! " + "打卡时间为：" + checkTime);
                        break;
                    default:
                        email.addTo("953633450@qq.com");
                        email.setSubject("uuID failed!");
                }
                //填写邮件主题
                email.setMsg("不用回复！");
                email.setSentDate(new Date());
//                email.setDebug(true);
                //发送邮件
                s = email.send();
            }
            //log out the send log
            Log.i("myinfo", s);


        } catch (EmailException e) {
            Log.i("myinfo", e.getMessage());
        }
    }
}
