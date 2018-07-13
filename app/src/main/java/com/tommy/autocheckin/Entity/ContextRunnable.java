package com.tommy.autocheckin.Entity;

import android.content.Context;

/**
 * Created by wojia on 2018/6/21.
 */

public class ContextRunnable implements Runnable{


        /**
         * 成员变量Context
         */
        public Context context;

        /**
         * 构造函数 将程序所需Context传入
         */
        public ContextRunnable(Context context) {
            //为成员变量赋值
            this.context = context;
        }

        @Override
        public void run() {
        }
    }
