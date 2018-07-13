package com.tommy.autocheckin.utils;

/**
 * Created by wojia on 2018/6/20.
 */



import java.util.List;


public class ThreadTools extends Thread {


    private List<String> mList = null;

    public ThreadTools(List<String> o) {
        this.mList = o;
    }

    @Override
    public void run() {
        if (mList != null && !mList.isEmpty()) {
            ShellUtils.execCmd((String[]) mList.toArray());
        }
    }
}