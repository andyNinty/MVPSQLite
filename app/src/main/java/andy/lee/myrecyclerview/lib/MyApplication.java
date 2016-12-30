package andy.lee.myrecyclerview.lib;

import android.app.Application;

import andy.lee.myrecyclerview.local.DBManager;

/**
 * andy.lee.myrecyclerview.lib
 * Created by andy on 16-12-29.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(this, "user.db");
    }
}
