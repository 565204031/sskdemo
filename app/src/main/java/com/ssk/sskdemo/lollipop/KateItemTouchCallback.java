package com.ssk.sskdemo.lollipop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.ssk.sskdemo.R;


/**
 * Created by 杀死凯 on 2016/4/12 0012.
 */
public class KateItemTouchCallback extends ItemTouchHelper.Callback {
    private ItemTouchHelperAdapterCallback adapterCallback;


    public KateItemTouchCallback(ItemTouchHelperAdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }
    //当我们执行拖拽(drag  swape)的时候重新绘制里面的条目
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //1,ItemView----viewHolder  2,侧滑的条目透明度与什么有关？dx :x方向侧滑的增量 范围：-width~width
        //Log.i("tz","viewHolder:"+viewHolder+",dx:"+dX+",dy:"+dY+",actionState:"+actionState);
        //线性布局的方向分为：水平和垂直
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int orientation = manager.getOrientation();
        if(orientation == LinearLayoutManager.VERTICAL){
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();//dx / item宽度   ;//0~1
                //属性动画 兼容低版本 nineOldandroid.jar包
                viewHolder.itemView.setAlpha(alpha);
            }
            //调用父类的重绘方法 是没有透明度渐变 setAlpha
        }else {
        //作业
    }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        //打开允许侧滑效果
        return true;
        //return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        //打开允许长按效果
        return true;
    }

    // 判断方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //Log.i("tz", "getMovementFlags");
        //适用GridLayoutMananger类和LinearLayoutManager类型
        if(recyclerView.getLayoutManager() instanceof GridLayoutManager){//一般不用侧滑
            //拖拽的方向
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            //侧滑的方向
            int swipFalgs = 0;//禁止侧滑
            return makeMovementFlags(dragFlags,swipFalgs);
        }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            //线性布局的方向分为：水平和垂直
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int orientation = manager.getOrientation();
            if(orientation == LinearLayoutManager.VERTICAL){
                //拖拽的方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
                //侧滑的方向
                int swipFalgs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags,swipFalgs);
            }else {//HORIZONTAL
                //侧滑的方向
                int swipFalgs = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
                //拖拽的方向
                int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags,swipFalgs);
            }
        }

        return 0;

    }

    /**
     * 当我们选中状态发生变化的时候回调
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            //改变选中item时候 改变颜色
            if(viewHolder instanceof  RecyclerActivity.KateAdapter.KateViewHolder){
                RecyclerActivity.KateAdapter.KateViewHolder itemViewHolder = (RecyclerActivity.KateAdapter.KateViewHolder) viewHolder;
                itemViewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().
                        getResources().getColor(android.R.color.holo_orange_light));
            }

        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof  RecyclerActivity.KateAdapter.KateViewHolder){
            RecyclerActivity.KateAdapter.KateViewHolder itemViewHolder = (RecyclerActivity.KateAdapter.KateViewHolder) viewHolder;
            itemViewHolder.itemView.setBackgroundColor(Color.parseColor("#448AFF"));
        }

        super.clearView(recyclerView, viewHolder);
    }

    //Item移动的时候 回调
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder target) {
        Log.i("tz", "onMove");
        if(srcHolder.getItemViewType() != target.getItemViewType()){//条目相同
            return false;
        }
        adapterCallback.onMove(srcHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    //在侧滑的时候回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i("tz", "onSwiped");
        adapterCallback.onSwipe(viewHolder.getAdapterPosition());
    }

    public interface ItemTouchHelperAdapterCallback {
        /**
         * 当拖拽的时候回调
         * 可以在此方法中实现：拖拽条目的动画
         * @param formPosition 从哪个位置开始拖拽
         * @param toPostion     拖拽到哪个位置
         * @return
         */
        boolean onMove(int formPosition,int toPostion);

        /**
         * 当条目被移除的时候应该回调的
         * @param position 侧滑条目的位置
         * @return
         */
        boolean onSwipe(int position);
    }
}
