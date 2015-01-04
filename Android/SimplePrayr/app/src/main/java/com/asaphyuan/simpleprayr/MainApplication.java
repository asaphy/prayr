package com.asaphyuan.simpleprayr;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.os.Bundle;

/**
 * Created by ayuan on 1/4/15.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Parse SDK.
        Parse.initialize(this, "r66jQM07dJ9pFjjuRrrhWJcsJdg7KpGlzHkgKEdQ", "iX6EQbU8QzmYqBGqTD9r7FHuifrUGRmztUBm3XhF");

        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}