package andy.lee.myrecyclerview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import andy.lee.myrecyclerview.base.SimpleDividerItemDecoration;
import andy.lee.myrecyclerview.bean.UserInfo;
import andy.lee.myrecyclerview.helper.OnStartDragListener;
import andy.lee.myrecyclerview.helper.SimpleItemTouchHelperCallback;
import andy.lee.myrecyclerview.local.DBManager;

/**
 * andy.lee.myrecyclerview
 * Created by andy on 16-12-28.
 */

public class MyListFragment extends Fragment implements OnStartDragListener {

    private List<UserInfo> mList = new ArrayList<>();
    private MyViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;


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
        initData();
        initRecyclerView();
        return view;

    }

    private void initRecyclerView() {
        //网格布局
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        //列表布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyViewAdapter(getContext(), this);
        mAdapter.addList(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), 1));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            UserInfo userInfo = new UserInfo(R.mipmap.pic1, "张三" + i);
            mList.add(userInfo);
        }
    }

    private void addItem() {
        for (int i = 0; i < 15; i++) {
            UserInfo userInfo = new UserInfo(R.mipmap.pic2, "李四" + i);
            mList.add(userInfo);
        }
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
            case R.id.add_item:
                addItem();
                mAdapter.addList(mList);
                break;

            case R.id.remove_item:
                mList.remove(mList.size() - 1);
                boolean isDelFromDb = DBManager.getInstance().deleteUserInfo(mList.size());
                Toast.makeText(getActivity(), "删除第" + String.valueOf(mList.size() - 1) + isDelFromDb, Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.save_to_db:
                boolean isInsert = false;
                for (int i = 0; i < mList.size(); i++) {
                    UserInfo userInfo = mList.get(i);
                    isInsert = DBManager.getInstance().insertUserInfo(i, userInfo.getResId(), userInfo.getName());
                }
                Toast.makeText(getActivity(), "insert " + isInsert, Toast.LENGTH_SHORT).show();
                break;

            case R.id.del_from_db:
                boolean isDel = DBManager.getInstance().deleteUserInfo();
                Toast.makeText(getActivity(), "delete " + isDel, Toast.LENGTH_SHORT).show();
                break;

            case R.id.update_db:
                boolean isUpdate = DBManager.getInstance().updateUserInfo(0, "王二");
                Toast.makeText(getActivity(), "update " + isUpdate, Toast.LENGTH_SHORT).show();
                break;

            case R.id.query_db:
                List<UserInfo> list = DBManager.getInstance().queryUserInfo(0);
                for (int i = 0; i < list.size(); i++) {
                    UserInfo userInfo = list.get(i);
                    int resId = userInfo.getResId();
                    String name = userInfo.getName();
                    Toast.makeText(getActivity(), "resId is " + resId + " name is " + name, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.delete_db:
                DBManager.getInstance().deleteDb();
                break;

            case R.id.query_all_from_db:
                List<UserInfo> userList = DBManager.getInstance().queryUserInfo();
                mList.clear();
                for (int i = 0; i < userList.size(); i++) {
                    UserInfo info = userList.get(i);
                    UserInfo userInfo = new UserInfo(R.mipmap.ic_launcher, info.getName());
                    mList.add(userInfo);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DBManager.getInstance().release();
    }

}
