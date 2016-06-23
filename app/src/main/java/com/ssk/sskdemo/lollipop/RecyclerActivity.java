package com.ssk.sskdemo.lollipop;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 杀死凯 on 2016/5/19.
 */
public class RecyclerActivity extends BaseToolBarActivity {
    private RecyclerView mRecyclerView;
    private List<String> mData;
    private KateAdapter mAdapter;
    private boolean iswaterfall;
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected int getContentView() {
        return R.layout.lollipop_activity_recycler;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        initData();
        mAdapter = new KateAdapter(this,mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

        ItemTouchHelper.Callback callback = new KateItemTouchCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        //绑定RecyclerView
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        //开启拖拽头一个ViewHolder
        //itemTouchHelper.startDrag();//开启拖拽功能

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mData = new ArrayList<String>();
        for(int i='A';i<'z';i++){
            mData.add(String.valueOf((char) i));
        }
    }
    public class KateAdapter extends RecyclerView.Adapter<KateAdapter.KateViewHolder> implements KateItemTouchCallback.ItemTouchHelperAdapterCallback {
        private List<String> mData;
        private LayoutInflater mLayoutInflater;

        public void addData(int position) {
            mData.add(position, "Insert One");
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
        public KateAdapter(Context context, List<String> mData) {
            this.mData = mData;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        /**
         * 创建ViewHolder缓存对象----Item条目的布局-----View
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public KateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(R.layout.lollopop_item, parent, false);
            return new KateViewHolder(view);
        }

        /**
         * 将数据与View进行绑定
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(KateViewHolder holder, int position) {
            holder.itemtv.setText(mData.get(position));
            if(iswaterfall){
                ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
                params.height = (int)(200+Math.random()*400);//把随机的高度赋予itemView布局
                holder.itemView.setLayoutParams(params);//把params设置给itemView布局
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        //交换条目
        @Override
        public boolean onMove(int formPosition, int toPostion) {

            //交换list集合中数据的位置
            Collections.swap(mData,formPosition,toPostion);
            //互换条目位置
            notifyItemMoved(formPosition,toPostion);
            return false;
        }

        //侧滑删除
        @Override
        public boolean onSwipe(int position) {
            mData.remove(position);//删除数据
            notifyItemRemoved(position);//移除条目
            return false;
        }

        class KateViewHolder extends RecyclerView.ViewHolder {
            TextView itemtv;

            public KateViewHolder(View itemView) {
                super(itemView);
                itemtv = (TextView) itemView.findViewById(R.id.tv_item);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        iswaterfall=false;
        switch (item.getItemId()){
            case R.id.add:
                mAdapter.addData(5);
                break;
            case R.id.del:
                mAdapter.removeData(0);
                break;
            case R.id.linear:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.gridv:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
                break;
            case R.id.gridh:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.waterfall:
                iswaterfall=true;
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_recycler,menu);
        return true;
    }
}
