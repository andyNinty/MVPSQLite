package andy.lee.myrecyclerview.lib;

import android.app.Application;
import android.content.Context;

import andy.lee.myrecyclerview.local.DBManager;

/**
 * andy.lee.myrecyclerview.lib
 * Created by andy on 16-12-29.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        DBManager.initDB(this, "user.db");
    }

    /**
     * 获取全局context
     * @return context
     */
    public static Context getContext() {
        return mContext;
    }
}
