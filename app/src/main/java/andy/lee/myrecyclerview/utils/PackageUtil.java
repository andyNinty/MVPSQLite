package andy.lee.myrecyclerview.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

/**
 * 工具类,用于获取安装包的一些信息
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


    /**
     * 获取版本当前version name
     *
     * @param context context
     * @return version name
     */
    @Nullable
    public static String getPackageVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            return info.versionName;
        } else {
            return null;
        }
    }
}
