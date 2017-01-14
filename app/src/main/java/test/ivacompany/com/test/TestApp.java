package test.ivacompany.com.test;

import android.app.Application;
import android.content.Context;

/**
 * Created by root on 13.01.17.
 */

public class TestApp extends Application {


    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }


    public static Context getAppContext() {
        return sContext;
    }
}
