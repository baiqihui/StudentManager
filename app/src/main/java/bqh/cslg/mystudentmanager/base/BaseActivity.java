package bqh.cslg.mystudentmanager.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class BaseActivity extends AppCompatActivity{

    public static List<Activity> activities = new ArrayList<>();
    BaseActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("BaseActivity",getClass().getSimpleName());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        baseActivity = new BaseActivity();
        baseActivity.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseActivity.removeActivity(this);
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
