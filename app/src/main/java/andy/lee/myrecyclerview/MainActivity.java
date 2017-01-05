package andy.lee.myrecyclerview;

import android.support.v4.app.Fragment;

import andy.lee.myrecyclerview.base.SingleFragmentActivity;
import andy.lee.myrecyclerview.fragment.MyListFragment;
import andy.lee.myrecyclerview.presenter.UserPresenter;


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
