package andy.lee.myrecyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import andy.lee.myrecyclerview.R;
import andy.lee.myrecyclerview.data.UserInfo;
import andy.lee.myrecyclerview.helper.ItemTouchHelperAdapter;
import andy.lee.myrecyclerview.helper.ItemTouchHelperViewHolder;
import andy.lee.myrecyclerview.helper.OnStartDragListener;

/**
 * andy.lee.myrecyclerview
 * Created by andy on 16-12-28.
 */

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private Context mContext;
    private List<UserInfo> mList = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;

    public MyViewAdapter(Context context, OnStartDragListener dragStartListener) {
        mContext = context;
        mDragStartListener = dragStartListener;
    }

    public void addList(List<UserInfo> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UserInfo userInfo = mList.get(position);
        holder.imageView.setImageResource(userInfo.getResId());
        holder.name.setText(userInfo.getName());
        holder.phoneNumber.setText(userInfo.getPhoneNumber());
        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private final ImageView imageView;
        private final TextView name;
        private final TextView phoneNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            name = (TextView) itemView.findViewById(R.id.name);
            phoneNumber = (TextView) itemView.findViewById(R.id.phone_number);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

}
