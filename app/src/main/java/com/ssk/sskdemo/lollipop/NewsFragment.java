package com.ssk.sskdemo.lollipop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/3.
 */
public class NewsFragment extends Fragment {
    private static int[] icons = {
        R.mipmap.meinu,
        R.mipmap.meinu2,
        R.mipmap.meinu3,
        R.mipmap.meinu4,
        R.mipmap.meinu5
    };
    public static int getBackGroudIconRes(int position){
        return icons[position];
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        View view = inflater.inflate(R.layout.lollopop_fragment_new,null);
        ImageView iv = (ImageView) view.findViewById(R.id.icon);
        iv.setImageResource(icons[position]);
        return view;
    }
}
