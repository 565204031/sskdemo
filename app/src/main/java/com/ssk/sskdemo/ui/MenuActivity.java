package com.ssk.sskdemo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/24.
 * 导航菜单弹出效果
 */
public class MenuActivity extends BaseToolBarActivity implements View.OnClickListener {

    private MenuView mv;
    @Override
    protected int getContentView() {
        return R.layout.ui_activity_menu;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mv= (MenuView) findViewById(R.id.mv);
        mv.setOnItemClick(this);
    }

    @Override
    public void onClick(View v) {
        String txt="";
        switch (v.getId()){
            case R.id.item1:
                txt="你点击了QQ";
                break;
            case R.id.item2:
                txt="你点击了QQ空间";
                break;
            case R.id.item3:
                txt="你点击了新浪微博";
                break;
            case R.id.item4:
                txt="你点击了微信";
                break;
            case R.id.item5:
                txt="你点击了微信朋友圈";
                break;
        }
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }
}
