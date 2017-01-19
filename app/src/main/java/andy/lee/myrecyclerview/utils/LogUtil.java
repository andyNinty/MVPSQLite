package andy.lee.myrecyclerview.utils;

import android.util.Log;

/**
 * andy.lee.myrecyclerview.utils
 * Created by andy on 17-1-17.
 */

public class LogUtil {

    private static final int VERBOSE = 0;

    private static final int DEBUG = 1;

    private static final int INFO = 2;

    private static final int WARN = 3;

    private static final int ERROR = 4;

    private static final int NOTHING = 5;

    private static int level;


    /**
     * 开启log
     */
    public static void init() {
        level = VERBOSE;
    }

    /**
     * 关闭log
     */
    public static void closeLog() {
        level = NOTHING;
    }

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }

}
