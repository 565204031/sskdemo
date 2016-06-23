package com.ssk.sskdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ssk.sskdemo.lollipop.LollipopMainActivity;
import com.ssk.sskdemo.lollipop.RecyclerActivity;
import com.ssk.sskdemo.ui.UIMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView ls_view;
    private List<HashMap<String,Object>> datas=new ArrayList<HashMap<String,Object>>(){
        {
            add(new HashMap<String, Object>(){{put("1","5.0新特性");}});
            add(new HashMap<String, Object>(){{put("1","自定义高级UI");}});
            add(new HashMap<String, Object>(){{put("1","自定义推送平台");}});
            add(new HashMap<String, Object>(){{put("1","自定义及时通讯");}});
            add(new HashMap<String, Object>(){{put("1","Item4");}});
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ls_view= (ListView) findViewById(R.id.ls_view);
        ls_view.setAdapter(new SimpleAdapter(this,datas,android.R.layout.simple_list_item_1,new String[]{"1"},new int[]{android.R.id.text1}));
        ls_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                //5.0新特性
                BaseActivity.start(this,LollipopMainActivity.class);
                break;
            case 1:
                //自定义高级UI
                BaseActivity.start(this,UIMainActivity.class);
                break;
            case 2:
                //自定义推送平台
                break;
            case 3:
                //自定义及时通讯
                break;
            case 4:
                //
                break;
        }
    }
}
