package andy.lee.myrecyclerview.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import andy.lee.myrecyclerview.R;
import andy.lee.myrecyclerview.contract.UserContract;
import andy.lee.myrecyclerview.data.UserInfo;
import andy.lee.myrecyclerview.local.DBManager;

/**
 * andy.lee.myrecyclerview.presenter
 * Created by andy on 17-1-5.
 */

public class UserPresenter implements UserContract.Presenter {

    private final UserContract.View mView;
    private List<UserInfo> mList = new ArrayList<>();

    public UserPresenter(UserContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Cursor cursor = mView.getCtx().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                UserInfo userInfo = new UserInfo(R.mipmap.pic1, name, phoneNum);
                mList.add(userInfo);
                mView.showUserInfo(mList);
            }
        }
        if (mList.isEmpty()) {
            Toast.makeText(mView.getCtx(), "您还没有联系人", Toast.LENGTH_SHORT).show();
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public void insertUser() {
        boolean isInsert = false;
        for (int i = 0; i < mList.size(); i++) {
            UserInfo userInfo = mList.get(i);
            isInsert = DBManager.getInstance().insertUserInfo(i, userInfo.getResId(), userInfo.getName(), userInfo.getPhoneNumber());
        }
        if (isInsert) {
            mView.showToast("保存到数据库成功");
        } else {
            mView.showToast("保存到数据库失败");
        }
    }

    @Override
    public void deleteOneUser() {
        mList.remove(mList.size() - 1);
        boolean isDelFromDb = DBManager.getInstance().deleteUserInfo(mList.size());
        if (isDelFromDb) {
            mView.showToast("删除第" + String.valueOf(mList.size()) + "成功");
            mView.updateListView();
        } else {
            mView.showToast("删除第" + String.valueOf(mList.size()) + "失败");
        }
    }

    @Override
    public void deleteUser() {
        boolean isDel = DBManager.getInstance().deleteUserInfo();
        if (isDel) {
            mView.showToast("删除所有数据成功");
            mView.clearList();
            mView.updateListView();
        } else {
            mView.showToast("删除所有数据失败");
        }
    }

    @Override
    public void deleteDB() {
        DBManager.getInstance().deleteDb();
        mView.clearList();
        mView.updateListView();
    }

    @Override
    public void updateUser() {
        boolean isUpdate = DBManager.getInstance().updateUserInfo(0, "王二");
        if (isUpdate) {
            mView.showToast("更新成功");
        }
    }

    @Override
    public void queryOneUser() {
        List<UserInfo> list = DBManager.getInstance().queryUserInfo(0);
        Log.e("userList", "jjj" + list.toString());
        for (int i = 0; i < list.size(); i++) {
            UserInfo userInfo = list.get(i);
            String name = userInfo.getName();
            String phoneNumber = userInfo.getPhoneNumber();
            mView.showToast("name is :" + name + " " + "number is :" + phoneNumber);
        }

    }

    @Override
    public void queryUser() {
        List<UserInfo> userList = DBManager.getInstance().queryUserInfo();
        mList.clear();
        for (int i = 0; i < userList.size(); i++) {
            UserInfo info = userList.get(i);
            UserInfo userInfo = new UserInfo(R.mipmap.ic_launcher, info.getName(), info.getPhoneNumber());
            mList.add(userInfo);
            mView.showUserInfo(mList);
            mView.updateListView();
        }
    }

    @Override
    public void updateContract() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getAct());
        final LayoutInflater inflater = mView.getAct().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(view)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: 17-1-3 contentProvider 更新通讯录

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    @Override
    public void releaseDb() {
        DBManager.getInstance().release();
    }


}
