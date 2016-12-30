package andy.lee.myrecyclerview.local;

import android.provider.BaseColumns;

/**
 * andy.lee.myrecyclerview.local
 * Created by andy on 16-12-29.
 */

public class UserEntry {

    public static class UserEntryColumn implements BaseColumns {

        //表名
        public static final String TABLE_NAME = "user";
        //头像id
        public static final String AVATAR_ID = "avatar_id";
        //名字
        public static final String USER_NAME = "user_name";

    }
}
