package com.tommy.autocheckin.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wojia on 2018/6/18.
 */

public class RegexTools {
    /**
     *
     * @param  pattern 正则表达式
     * @param targetStr 目标字符串
     * @return 是否找到
     */
    public static boolean isMatch(String pattern, String targetStr){
        return Pattern.matches(pattern, targetStr);
    }

    /**
     * 返回 groups 的集合
     * @param pattern 正则表达式
     * @param targetStr 目标字符串
     * @return List
     */
    public static ArrayList<String> getInfoByRegex(String pattern, String targetStr){

        //list
        ArrayList<String> resultList = new ArrayList<>();

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(targetStr);
        if (m.find()) {
            //fuck,groupcount 记得加 1
            for (int i = 1;i<m.groupCount()+1;i++){
                resultList.add(m.group(i));
                return resultList;
            }

        }

        return null;
    }
}
