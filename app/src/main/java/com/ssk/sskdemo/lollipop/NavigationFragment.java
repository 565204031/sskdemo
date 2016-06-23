package com.ssk.sskdemo.lollipop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/19.
 */
public class NavigationFragment extends Fragment{
    private TextView tv_title;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lollopop_fragment_navigation,container,false);
        tv_title= (TextView) view.findViewById(R.id.tv_title);
        return view;
    }
    public void setTitle(String txt){
        if(tv_title!=null){
            tv_title.setText(txt);
        }
    }

}
