package andy.lee.myrecyclerview.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DataBaseProvider extends ContentProvider {

    public static final int USER_DIR = 0;
    public static final int USER_ITEM = 1;
    private static final String MIME_DIR = "vnd.android.cursor.dir/vnd."
            + UserEntry.AUTHORITY + "."
            + UserEntry.UserEntryColumn.TABLE_NAME;

    private static final String MIME_ITEM = "vnd.android.cursor.item/vnd."
            + UserEntry.AUTHORITY + "."
            + UserEntry.UserEntryColumn.TABLE_NAME;


    private static final String TABLE_NAME = UserEntry.UserEntryColumn.TABLE_NAME;
    private static UriMatcher sUriMatcher;
    private DBHelper mDBHelper;

    static {
        final String authority = UserEntry.AUTHORITY;
        final String path = UserEntry.PATH;
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(authority, path, USER_DIR);
        sUriMatcher.addURI(authority, path + "/#", USER_ITEM);
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext(), "user.db");
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Uri returnUri;
        long _id;
        switch (sUriMatcher.match(uri)) {
            case USER_DIR:
            case USER_ITEM:
                _id = db.insert(TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = UserEntry.UserEntryColumn.buildUri(_id);
                    Log.e("DataBase", "returnUri is" + returnUri.toString());
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("there is no match");
        }
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int deleteRows;
        switch (sUriMatcher.match(uri)) {
            case USER_DIR:
                deleteRows = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case USER_ITEM:
                String id = uri.getPathSegments().get(1);
                deleteRows = db.delete(TABLE_NAME, UserEntry.UserEntryColumn._ID + " = ?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("there is no match");
        }
        return deleteRows;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int updateRows;
        switch (sUriMatcher.match(uri)) {
            case USER_DIR:
                updateRows = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case USER_ITEM:
                String id = uri.getPathSegments().get(1);
                updateRows = db.update(TABLE_NAME, values, UserEntry.UserEntryColumn._ID + " = ?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("there is no match");
        }
        return updateRows;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case USER_DIR:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case USER_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = db.query(TABLE_NAME, projection, UserEntry.UserEntryColumn._ID + " = ?", new String[]{id}, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("there is no match");
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case USER_DIR:
                return MIME_DIR;
            case USER_ITEM:
                return MIME_ITEM;
            default:
                throw new UnsupportedOperationException("there is no match");
        }
    }
}
