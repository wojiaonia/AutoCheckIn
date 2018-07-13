package com.tommy.autocheckin.ui;

/**
 * Created by wojia on 2018/6/19.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tommy.autocheckin.services.TimerTaskService;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //入口service
        Intent i = new Intent(this, TimerTaskService.class);
        startService(i);

    }

}
