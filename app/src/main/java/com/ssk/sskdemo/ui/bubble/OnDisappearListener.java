package com.ssk.sskdemo.ui.bubble;

import android.graphics.PointF;

/**
 * Created by 杀死凯 on 2016/5/25.
 * 气泡状态监听
 */
public interface OnDisappearListener {
    void onDisappear(PointF dragCenter);
    void onReset(boolean isOutOfRange);
}
