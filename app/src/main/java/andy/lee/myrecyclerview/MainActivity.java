package andy.lee.myrecyclerview;

import android.support.v4.app.Fragment;

import andy.lee.myrecyclerview.base.SingleFragmentActivity;
import andy.lee.myrecyclerview.fragment.MyListFragment;


public class MainActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new MyListFragment();

    }
}
