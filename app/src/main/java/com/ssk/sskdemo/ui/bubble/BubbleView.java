package com.ssk.sskdemo.ui.bubble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.support.v4.view.MotionEventCompat;
import android.text.LoginFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by 杀死凯 on 2016/5/24.
 * 消息气泡
 */
public class BubbleView extends View{

    //状态栏高度
    private int statusBarHeight;
    //拖拽圆坐标
    private PointF dragCenter;
    //固定圆坐标
    private PointF stickCenter;
    //拖拽圆半径
    private float dragRadius=BubbleUtils.dipToDimension(10, getContext());
    //固定圆半径
    private float stickRadius=BubbleUtils.dipToDimension(10, getContext());
    //根据百分比和固定的圆的半径的范围
    private float stickMinRadius = BubbleUtils.dipToDimension(3, getContext());
    //拖拽临界值
    private float farest;
    //圆的画笔
    private Paint mpainred;
    //字体画笔
    private Paint mpainwhite;
    //文字
    private String text;
    //是否超过临界值
    private boolean isOutOfRange;
    //是否气泡爆炸
    private boolean isDisappear;
    // 定义气泡的大小
    private Rect bubbleSize;
    private ValueAnimator valueAnimator;

    //回调监听
    private OnDisappearListener onDisappearListener;
    public BubbleView(Context context) {
        super(context);
        initParams();
    }
    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams();
    }
    private void initParams(){
        this.statusBarHeight = BubbleUtils.getStatusBarHeight(getContext());
        this.mpainred = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mpainred.setColor(Color.RED);
        this.mpainwhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mpainwhite.setTextAlign(Paint.Align.CENTER);
        this.mpainwhite.setColor(Color.WHITE);
        this.mpainwhite.setTextSize(dragRadius * 1.2f);
        farest=BubbleUtils.dipToDimension(80, getContext());
        this.bubbleSize = new Rect(0, 0, 50, 50);
    }
    //设置消息文字
    public void setBubbleText(String txt){
        text=txt;
    }
    //设置中心点
    public void setCenterPoint(float x,float y){
        this.dragCenter = new PointF(x, y);
        this.stickCenter = new PointF(x, y);
        invalidate();
    }
    //根据两圆距离算出固定圆半径
    public float getCurrentRadius(float distance){
        // 2.1.2.2 根据两点之间的距离动态的计算固定圆的半径的百分比--这个算法是可以改的(0-1)
        // 例如：我现在我的半径是100（20-100）
        float fraction = 0.2f + 0.8f * (distance / farest);

        // 2.1.2.3 根据百分比和固定的圆的半径的范围－－－计算当前固定圆的半径
        float value = GeometryUtil.evaluateValue(fraction,
                this.dragRadius, this.stickMinRadius);

        return value;

    }
    //根据手机拖拽，设置拖拽圆中心点
    public void updateCenterPoint(float x, float y) {
        this.dragCenter.x = x;
        this.dragCenter.y = y;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, -statusBarHeight);
        if (!this.isDisappear) {
            if (!isOutOfRange) {
                //没有超过临界值
                //绘制中间黏黏的那块
                ShapeDrawable shapeDrawable = drawShapeDrawable();
                shapeDrawable.setBounds(bubbleSize);
                shapeDrawable.draw(canvas);
                //绘制固定的圆
                canvas.drawCircle(stickCenter.x, stickCenter.y, stickRadius, mpainred);
            }

            //绘制拖拽圆
            canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, mpainred);

            //绘制消息,居中显示
            canvas.drawText(text, dragCenter.x, dragCenter.y+dragRadius/2f, mpainwhite);

        }else{
            //绘制气泡爆炸

        }
        canvas.restore();
    }
    //画两个圆之间的连线
    private ShapeDrawable drawShapeDrawable() {
        Path path = new Path();

        //两圆的距离
        float distance = GeometryUtil.getDistanceBetween2Points(dragCenter,
                stickCenter);
        stickRadius=getCurrentRadius(distance);

        // 目的：绘制贝塞尔曲线
        float xDistance = stickCenter.x - dragCenter.x;
        double tan = 0;
        if (xDistance != 0) {
            tan = (stickCenter.y - dragCenter.y) / xDistance;
        }
        // 获取指定圆心和正切值、直线和圆心的交点
        PointF[] stickPoint = GeometryUtil.getIntersectionPoints(stickCenter,
                stickRadius, tan);
        PointF[] dragPoint = GeometryUtil.getIntersectionPoints(dragCenter,
                dragRadius, tan);

        // 绘制贝塞尔曲线
        // 第一步: 获取控制点（0.618－－－黄金分割）
        PointF pointByPercent = GeometryUtil.getPointByPercent(dragCenter,
                stickCenter, 0.618f);
        // 第二步：画第一条贝塞尔曲线
        // 贝塞尔曲线的开始位置
        path.moveTo(stickPoint[0].x, stickPoint[0].y);
        // quadTo:前面两个参数是贝塞尔曲线的控制点坐标（x1, y1）
        // 后面两个参数是贝塞尔曲线的结束位置（x2, y2）
        path.quadTo(pointByPercent.x, pointByPercent.y, dragPoint[0].x,
                dragPoint[0].y);
        // 第三步：画第二条贝塞尔曲线
        // 贝塞尔曲线的开始位置
        path.lineTo(dragPoint[1].x, dragPoint[1].y);
        path.quadTo(pointByPercent.x, pointByPercent.y, stickPoint[1].x,
                stickPoint[1].y);
        path.close();

        // 构建ShapeDrawable
        ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(path,
                50f, 50f));
        shapeDrawable.getPaint().setColor(Color.RED);
        return shapeDrawable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = MotionEventCompat.getActionMasked(event);
        switch(actionMasked){
            case MotionEvent.ACTION_DOWN:
                isOutOfRange=false;
                isDisappear=false;
                updateCenterPoint(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:

                PointF pointOne = new PointF(dragCenter.x, dragCenter.y);
                PointF pointTwo = new PointF(stickCenter.x, stickCenter.y);
                if (GeometryUtil.getDistanceBetween2Points(pointOne, pointTwo) > farest) {
                    isOutOfRange = true;
                    updateCenterPoint(event.getRawX(), event.getRawY());
                    return false;
                }else{
                    isOutOfRange=false;
                }
                Log.i("INFO",GeometryUtil.getDistanceBetween2Points(pointOne, pointTwo)+"|farest="+farest);
                updateCenterPoint(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                //气泡爆炸
                handleAction();
                break;
            default:
                this.isOutOfRange = false;
                break;
        }
      return true;
    }
    private void handleAction() {
        if(isOutOfRange){
            //超出范围，气泡爆炸效果
            disappeared();
        }else{
            //气泡返回原来的位置
            valueAnimator = ValueAnimator.ofFloat(1.0f);
            valueAnimator.setInterpolator(new OvershootInterpolator(4.0f));
            final PointF startPoint = new PointF(dragCenter.x, dragCenter.y);
            final PointF endPoint = new PointF(stickCenter.x, stickCenter.y);
            // 添加动画过程监听
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = animation.getAnimatedFraction();
                    // 计算气泡拖拽中心点的坐标
                    PointF percent = GeometryUtil.getPointByPercent(startPoint,
                            endPoint, fraction);
                    updateCenterPoint(percent.x, percent.y);
                }
            });

            // 动画结束之后？
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if(onDisappearListener!=null){
                        onDisappearListener.onReset(isOutOfRange);
                    }
                }
            });

            // 设置动画的执行时间
            if (GeometryUtil.getDistanceBetween2Points(startPoint, endPoint) < 20) {
                valueAnimator.setDuration(50);
            } else {
                valueAnimator.setDuration(300);
            }
            valueAnimator.start();
        }
    }
    private void disappeared(){
        this.isDisappear = true;
        invalidate();
        if (this.onDisappearListener != null) {
            this.onDisappearListener.onDisappear(dragCenter);
        }
    }

    public void setOnDisappearListener(OnDisappearListener onDisappearListener) {
        this.onDisappearListener = onDisappearListener;
    }
}
