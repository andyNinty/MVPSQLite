package andy.lee.myrecyclerview.bean;

/**
 * andy.lee.myrecyclerview
 * Created by andy on 16-12-29.
 */

public class UserInfo {
    private int resId;
    private String name;
    private String phoneNumber;

    public UserInfo() {

    }

    public UserInfo(int resId, String name,String phoneNumber) {
        this.resId = resId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
