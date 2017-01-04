package andy.lee.myrecyclerview.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * andy.lee.myrecyclerview.local
 * Created by andy on 16-12-29.
 */

public class UserEntry {

    public static final String AUTHORITY = "andy.lee.myrecyclerview.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "user";

    public static class UserEntryColumn implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //表名
        public static final String TABLE_NAME = "user";
        //头像id
        public static final String AVATAR_ID = "avatar_id";
        //名字
        public static final String USER_NAME = "user_name";
        //手机号码
        public static final String PHONE_NUMBER = "phone_number";

    }
}
