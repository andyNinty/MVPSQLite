package andy.lee.myrecyclerview.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 工具类
 * Created by andy on 17-1-17.
 */

public class PackageUtil {

    /**
     * 获取版本当前版本号
     *
     * @param context context
     * @return version code
     */
    public static int getPackageVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            return info.versionCode;
        } else {
            return 1;
        }
    }

}
