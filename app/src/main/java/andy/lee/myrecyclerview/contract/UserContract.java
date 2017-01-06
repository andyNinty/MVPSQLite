package andy.lee.myrecyclerview.contract;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import andy.lee.myrecyclerview.base.BasePresenter;
import andy.lee.myrecyclerview.base.BaseView;
import andy.lee.myrecyclerview.data.UserInfo;

/**
 * andy.lee.myrecyclerview.bean
 * Created by andy on 17-1-5.
 */

public interface UserContract {

    interface View extends BaseView<Presenter> {

        Context getCtx();

        Activity getAct();

        void clearList();

        void updateListView();

        void showUserInfo(List<UserInfo> userInfo);

        void showToast(String message);
    }


    interface Presenter extends BasePresenter {

        void insertUser();

        void deleteOneUser();

        void deleteUser();

        void deleteDB();

        void updateUser();

        void queryOneUser();

        void queryUser();

        void updateContract();

        void releaseDb();

    }

}
