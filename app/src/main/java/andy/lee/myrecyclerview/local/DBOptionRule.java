package andy.lee.myrecyclerview.local;

import java.util.List;

import andy.lee.myrecyclerview.bean.UserInfo;

/**
 * andy.lee.myrecyclerview.local
 * Created by andy on 16-12-29.
 */

public interface DBOptionRule {

    boolean insertUserInfo(int position, int resId, String name,String phoneNumber);

    boolean deleteUserInfo();

    boolean deleteUserInfo(int position);

    boolean updateUserInfo(int position, String name);

    List<UserInfo> queryUserInfo(int position);

    List<UserInfo> queryUserInfo();

    void deleteDb();

    void release();
}
