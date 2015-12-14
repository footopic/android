package jp.ac.dendai.im.cps.footopic.utils;

import android.app.Activity;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by naoya on 2015/04/09.
 */
public class App extends android.app.Application {

    private static App instance;
    private static Activity activity;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        App.activity = activity;
    }

    @Override
    public void onCreate() {
        Fresco.initialize(this);
        super.onCreate();
    }
}
