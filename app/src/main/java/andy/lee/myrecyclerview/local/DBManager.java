package andy.lee.myrecyclerview.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import andy.lee.myrecyclerview.bean.UserInfo;

/**
 * andy.lee.myrecyclerview.local
 * Created by andy on 16-12-29.
 */

public class DBManager implements DBOptionRule {

    private static final String TAG = "DBManager";
    private static DBManager instance = null;
    private String mDbName;
    private Context mContext;
    private DBHelper mDBHelper;

    private DBManager(Context context, String name) {
        mContext = context;
        mDbName = name;
        mDBHelper = new DBHelper(context, name);

    }

    public static void initDB(Context context, String name) {
        if (instance == null) {
            instance = new DBManager(context.getApplicationContext(), name);
        }
    }

    public static DBManager getInstance() {
        synchronized (DBManager.class) {
            if (instance == null) {
                Log.e(TAG, "DBManager was not be initalized!");
            }
        }
        return instance;
    }

    @Override
    public synchronized boolean insertUserInfo(int position, int resId, String name, String phoneNumber) {
        ContentValues values = new ContentValues();
        if (resId != 0) {
            values.put(UserEntry.UserEntryColumn.AVATAR_ID, resId);
        }
        if (name != null) {
            values.put(UserEntry.UserEntryColumn.USER_NAME, name);
        }
        if (phoneNumber != null) {
            values.put(UserEntry.UserEntryColumn.PHONE_NUMBER, phoneNumber);
        }
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final String selection = UserEntry.UserEntryColumn._ID + " = ?";
        final String[] selectionArgs = new String[]{String.valueOf(position)};

        long _id;
        Cursor cursor = db.query(UserEntry.UserEntryColumn.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() <= 0) {
            values.put(UserEntry.UserEntryColumn._ID, position);
            _id = db.insert(UserEntry.UserEntryColumn.TABLE_NAME, null, values);
        } else {
            _id = db.update(UserEntry.UserEntryColumn.TABLE_NAME, values, selection, selectionArgs);
        }
        cursor.close();
        return _id > 0;
    }

    @Override
    public boolean deleteUserInfo() {
        //在此先提供删除数据库表中的所有数据
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        return db.delete(UserEntry.UserEntryColumn.TABLE_NAME, null, null) > 0;
    }

    @Override
    public boolean deleteUserInfo(int position) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final String whereClause = UserEntry.UserEntryColumn._ID + " = ?";
        final String[] whereArgs = new String[]{String.valueOf(position)};
        return db.delete(UserEntry.UserEntryColumn.TABLE_NAME, whereClause, whereArgs) > 0;
    }

    @Override
    public boolean updateUserInfo(int position, String name) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final String whereClause = UserEntry.UserEntryColumn._ID + " = ?";
        final String[] whereArgs = new String[]{String.valueOf(position)};

        ContentValues values = new ContentValues();
        values.put(UserEntry.UserEntryColumn._ID, position);
        values.put(UserEntry.UserEntryColumn.USER_NAME, name);
        return db.update(UserEntry.UserEntryColumn.TABLE_NAME, values, whereClause, whereArgs) > 0;
    }

    @Override
    public List<UserInfo> queryUserInfo(int position) {
        List<UserInfo> userInfoList = new ArrayList<>();
        final SQLiteDatabase db = mDBHelper.getReadableDatabase();
        final String selection = UserEntry.UserEntryColumn._ID + " = ?";
        final String[] selectionArgs = new String[]{String.valueOf(position)};
        Cursor cursor = db.query(UserEntry.UserEntryColumn.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return userInfoList;
        }
        while (cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserEntry.UserEntryColumn.USER_NAME)));
            userInfo.setResId(cursor.getInt(cursor.getColumnIndex(UserEntry.UserEntryColumn.AVATAR_ID)));
            userInfoList.add(userInfo);
        }
        cursor.close();
        return userInfoList;
    }

    @Override
    public List<UserInfo> queryUserInfo() {
        List<UserInfo> userInfoList = new ArrayList<>();
        final SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query(UserEntry.UserEntryColumn.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return userInfoList;
        }
        while (cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserEntry.UserEntryColumn.USER_NAME)));
            userInfo.setResId(cursor.getInt(cursor.getColumnIndex(UserEntry.UserEntryColumn.AVATAR_ID)));
            userInfo.setPhoneNumber(cursor.getString(cursor.getColumnIndex(UserEntry.UserEntryColumn.PHONE_NUMBER)));
            userInfoList.add(userInfo);
        }
        cursor.close();
        return userInfoList;
    }


    @Override
    public void deleteDb() {
        mContext.deleteDatabase(mDbName);
        instance = null;
    }

    @Override
    public void release() {
        if (instance != null) {
            instance = null;
        }
    }
}
