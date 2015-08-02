package com.johnsly.todo;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by root on 8/2/15.
 */
public class MyApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "c2O2PGTCbWpmOH8Enz9v8DA5tgp9Zg8GyYMbpxam", "fQaRRbyUxPDohwA6UQYYCzFzCVK1W6Z9UMHbDmfP");
        ParseObject.registerSubclass(Task.class);
    }
}
