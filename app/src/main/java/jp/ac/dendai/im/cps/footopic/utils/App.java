package jp.ac.dendai.im.cps.footopic.utils;

import android.app.Activity;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by naoya on 2015/04/09.
 */
public class App extends android.app.Application {

    private static App instance;
    private static Activity activity;
    private static Handler handler = new Handler();

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        Fresco.initialize(this);
        super.onCreate();
    }
}
