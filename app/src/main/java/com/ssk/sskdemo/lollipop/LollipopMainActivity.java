package com.ssk.sskdemo.lollipop;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ssk.sskdemo.BaseActivity;
import com.ssk.sskdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 杀死凯 on 2016/5/19.
 */
public class LollipopMainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView ls_view;
    private List<HashMap<String,Object>> datas=new ArrayList<HashMap<String,Object>>(){
        {
            add(new HashMap<String, Object>(){{put("1","RecyclerView使用");}});
            add(new HashMap<String, Object>(){{put("1","DrawerLayout+NavigationView");}});
            add(new HashMap<String, Object>(){{put("1","新特性控件");}});
            add(new HashMap<String, Object>(){{put("1","TabLayout使用");}});
            add(new HashMap<String, Object>(){{put("1","drawable Tint");}});
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
                //RecyclerView使用
                BaseActivity.start(this,RecyclerActivity.class);
                break;
            case 1:
                //DrawerLayout+NavigationView使用
                BaseActivity.start(this,DrawerActivity.class);
                break;
            case 2:
                //TextInputLayout 使用
                BaseActivity.start(this,TextInputActivity.class);
                break;
            case 3:
                //TabLayout使用
                BaseActivity.start(this,TabLayoutActivity.class);
                break;
            case 4:
                //
                break;
        }
    }
}
