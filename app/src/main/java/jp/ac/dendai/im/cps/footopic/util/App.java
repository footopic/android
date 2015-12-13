package jp.ac.dendai.im.cps.footopic.util;

import android.app.Activity;

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
        super.onCreate();
    }
}
