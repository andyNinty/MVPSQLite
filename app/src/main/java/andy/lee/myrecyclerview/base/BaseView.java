package andy.lee.myrecyclerview.base;

/**
 * andy.lee.myrecyclerview.base
 * BaseView中处理每个view都会执行的方法，比如显示错误信息，显示dialog等
 * Created by andy on 17-1-5.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showToast(String message);
}
