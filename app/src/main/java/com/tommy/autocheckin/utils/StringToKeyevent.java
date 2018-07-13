package com.tommy.autocheckin.utils;

import java.util.ArrayList;

/**
 * Created by wojia on 2018/6/24.
 */

public class StringToKeyevent {
    /**
     * 其实用 enum 会更好 我还是这样暴力转换算了
     * @param string 字符段
     * @return adb key event 执行指令集
     */
    public static ArrayList<String> sToK(String string) {
        ArrayList<String> mCmds = new ArrayList<>();
        char[] ele = string.toCharArray();
        for (char c : ele) {
            switch (c){
                case 'a':
                    mCmds.add("input keyevent 29");
                    break;
                case 'b':
                    mCmds.add("input keyevent 30");
                    break;
                case 'c':
                    mCmds.add("input keyevent 31");
                    break;
                case 'd':
                    mCmds.add("input keyevent 32");
                    break;
                case 'e':
                    mCmds.add("input keyevent 33");
                    break;
                case 'f':
                    mCmds.add("input keyevent 34");
                    break;
                case 'g':
                    mCmds.add("input keyevent 35");
                    break;
                case 'h':
                    mCmds.add("input keyevent 36");
                    break;
                case 'i':
                    mCmds.add("input keyevent 37");
                    break;
                case 'j':
                    mCmds.add("input keyevent 38");
                    break;
                case 'k':
                    mCmds.add("input keyevent 39");
                    break;
                case 'l':
                    mCmds.add("input keyevent 40");
                    break;
                case 'm':
                    mCmds.add("input keyevent 41");
                    break;
                case 'n':
                    mCmds.add("input keyevent 42");
                    break;
                case 'o':
                    mCmds.add("input keyevent 43");
                    break;
                case 'p':
                    mCmds.add("input keyevent 44");
                    break;
                case 'q':
                    mCmds.add("input keyevent 45");
                    break;
                case 'r':
                    mCmds.add("input keyevent 46");
                    break;
                case 's':
                    mCmds.add("input keyevent 47");
                    break;
                case 't':
                    mCmds.add("input keyevent 48");
                    break;
                case 'u':
                    mCmds.add("input keyevent 49");
                    break;
                case 'v':
                    mCmds.add("input keyevent 50");
                    break;
                case 'w':
                    mCmds.add("input keyevent 51");
                    break;
                case 'x':
                    mCmds.add("input keyevent 52");
                    break;
                case 'y':
                    mCmds.add("input keyevent 53");
                    break;
                case 'z':
                    mCmds.add("input keyevent 54");
                    break;
                case '1':
                    mCmds.add("input keyevent 8");
                    break;
                case '2':
                    mCmds.add("input keyevent 9");
                    break;
                case '3':
                    mCmds.add("input keyevent 10");
                    break;
                case '4':
                    mCmds.add("input keyevent 11");
                    break;
                case '5':
                    mCmds.add("input keyevent 12");
                    break;
                case '6':
                    mCmds.add("input keyevent 13");
                    break;
                case '7':
                    mCmds.add("input keyevent 14");
                    break;
                case '8':
                    mCmds.add("input keyevent 15");
                    break;
                case '9':
                    mCmds.add("input keyevent 16");
                    break;
                case '0':
                    mCmds.add("input keyevent 7");
                    break;
                default:
                    mCmds.add("");
                    break;
            }
        }
        return mCmds;
    }
}
