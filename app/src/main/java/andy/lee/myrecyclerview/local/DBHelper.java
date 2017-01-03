package andy.lee.myrecyclerview.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * andy.lee.myrecyclerview.local
 * Created by andy on 16-12-29.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    public DBHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.UserEntryColumn.TABLE_NAME + "("
                + UserEntry.UserEntryColumn._ID + " INTEGER PRIMARY KEY, "
                + UserEntry.UserEntryColumn.AVATAR_ID + " INTEGER ,"
                + UserEntry.UserEntryColumn.USER_NAME + " TEXT ,"
                + UserEntry.UserEntryColumn.PHONE_NUMBER + " TEXT )";

        db.execSQL(SQL_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.UserEntryColumn.TABLE_NAME);
        onCreate(db);
    }
}
