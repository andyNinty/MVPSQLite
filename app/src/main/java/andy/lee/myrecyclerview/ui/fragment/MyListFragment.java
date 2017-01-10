package andy.lee.myrecyclerview.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import andy.lee.myrecyclerview.R;
import andy.lee.myrecyclerview.adapter.MyViewAdapter;
import andy.lee.myrecyclerview.base.BaseFragment;
import andy.lee.myrecyclerview.base.SimpleDividerItemDecoration;
import andy.lee.myrecyclerview.contract.UserContract;
import andy.lee.myrecyclerview.data.UserInfo;
import andy.lee.myrecyclerview.helper.OnStartDragListener;
import andy.lee.myrecyclerview.helper.SimpleItemTouchHelperCallback;
import andy.lee.myrecyclerview.ife.PermissionListener;

/**
 * andy.lee.myrecyclerview
 * Created by andy on 16-12-28.
 */

public class MyListFragment extends BaseFragment implements OnStartDragListener,UserContract.View {

    private List<UserInfo> mList = new ArrayList<>();
    private MyViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private UserContract.Presenter mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fragment中使用menu需要调用此方法
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestRuntimePermission(new String[]{Manifest.permission.READ_CONTACTS}, new PermissionListener() {
            @Override
            public void onGranted() {
                mPresenter.start();
            }

            @Override
            public void onDenied(List<String> permissionList) {
                Toast.makeText(getActivity(), "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        //网格布局
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        //列表布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyViewAdapter(getContext(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), 1));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.remove_item:
               mPresenter.deleteOneUser();
                break;

            case R.id.save_to_db:
                mPresenter.insertUser();
                break;

            case R.id.del_from_db:
                mPresenter.deleteUser();
                break;

            case R.id.update_db:
                mPresenter.updateUser();
                break;

            case R.id.query_db:
               mPresenter.queryOneUser();
                break;

            case R.id.delete_db:
                mPresenter.deleteDB();
                break;

            case R.id.query_all_from_db:
                mPresenter.queryUser();
                break;
            case R.id.custom_dialog:
                mPresenter.updateContract();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.releaseDb();

    }

    @Override
    public Context getCtx() {
        return getContext();
    }

    @Override
    public Activity getAct() {
        return getActivity();
    }

    @Override
    public void clearList() {
        mList.clear();
    }

    @Override
    public void updateListView() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUserInfo(List<UserInfo> userInfo) {
        mList = userInfo;
        mAdapter.addList(mList);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
