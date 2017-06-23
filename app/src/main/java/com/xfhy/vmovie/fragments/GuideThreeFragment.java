package com.xfhy.vmovie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.activity.MainActivity;


/**
 * Created by xfhy on 2017年6月18日15:14:41
 * 引导界面3
 */
public class GuideThreeFragment extends Fragment implements View.OnClickListener {

    //加载布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_three, container, false);
        ImageView goSecond = (ImageView) view.findViewById(R.id.iv_guide_start);
        goSecond.setOnClickListener(this);
        return view;
    }

    public static GuideThreeFragment newInstance() {

        Bundle args = new Bundle();

        GuideThreeFragment fragment = new GuideThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_guide_start:
                //跳转到第二个界面
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                //关闭引导界面Activity
                getActivity().finish();
                break;
        }
    }
}
