package bqh.cslg.mystudentmanager.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class BaseApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
