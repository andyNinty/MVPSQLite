package andy.lee.myrecyclerview.ui;

import android.support.v4.app.Fragment;

import andy.lee.myrecyclerview.base.SingleFragmentActivity;
import andy.lee.myrecyclerview.presenter.UserPresenter;
import andy.lee.myrecyclerview.ui.fragment.MyListFragment;

/**
 * activity 负责创建view 和presenter的实例，并将其联系起来
 * 更多可参考http://www.jianshu.com/p/569ab68da482
 */
public class MainActivity extends SingleFragmentActivity {

    private MyListFragment listFragment = new MyListFragment();

    @Override
    protected void init() {
        super.init();
        new UserPresenter(listFragment);
    }

    @Override
    protected Fragment createFragment() {
        return listFragment;

    }
}
