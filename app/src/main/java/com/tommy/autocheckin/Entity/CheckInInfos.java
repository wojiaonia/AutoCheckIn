package com.tommy.autocheckin.Entity;

import java.util.Calendar;

/**
 * Created by wojia on 2018/6/20.
 */

public class CheckInInfos {
    private int checkInTimes;
//    private String location;
    private String weekday;
    private Calendar date;

    public CheckInInfos(){
        checkInTimes = 0;
//        location = "";
        weekday = "";
        date =Calendar.getInstance();
    }

    public int getCheckInTimes() {
        return checkInTimes;
    }

    public void setCheckInTimes(int checkInTimes) {
        this.checkInTimes = checkInTimes;
    }

//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }


    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(int day) {
        switch (day){
            case 1:
                weekday = "星期日";
                break;
            case 2:
                weekday = "星期一";
                break;
            case 3:
                weekday = "星期二";
                break;
            case 4:
                weekday = "星期三";
                break;
            case 5:
                weekday = "星期四";
                break;
            case 6:
                weekday = "星期五";
                break;
            case 7:
                weekday = "星期六";
                break;
            default:
                weekday = "不知道星期几";
                break;
        }
    }




}
