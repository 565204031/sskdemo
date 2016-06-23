package com.ssk.sskdemo.lollipop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/19.
 */
public class TextInputActivity extends BaseToolBarActivity {
    private AppCompatButton snackbar;
    @Override
    protected int getContentView() {
        return R.layout.lollopop_activity_textinput;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        snackbar= (AppCompatButton) findViewById(R.id.snackbar);
        snackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v,"是否开启加速模式？",Snackbar.LENGTH_LONG);
                //还可以设置Action动作
                snackbar.setAction("确定",new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TextInputActivity.this,"开始加速",Toast.LENGTH_LONG).show();
                    }
                });
                //修改文本的颜色
                snackbar.setActionTextColor(Color.RED);
                //监听snackbar的显示和隐藏
                snackbar.setCallback(new Snackbar.Callback() {
                    //snackbar消失回调
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        Log.i("tz","snackbar dismisssed");

                    }
                    //snackbar显示的时候回调
                    @Override
                    public void onShown(Snackbar snackbar) {
                        super.onShown(snackbar);
                        Log.i("tz","snackbar shown!!");

                    }
                });
                snackbar.show();
            }
        });
    }
}
