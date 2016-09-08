package com.trampcr.sphomework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;
    private LayoutInflater layoutInflater;
    private ImageView ivDot1;
    private ImageView ivDot2;
    private ImageView ivDot3;
    private SharedPreferences sp;
    private boolean isFirstOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("sp", MODE_PRIVATE);
        isFirstOpen = sp.getBoolean("isFirstOpen", false);

        if (!isFirstOpen) {
            initViews();

            //初始化Adapter
            viewPagerAdapter = new ViewPagerAdapter(views);
            viewPager.setAdapter(viewPagerAdapter);
            //绑定回调
            viewPager.addOnPageChangeListener(this);
        }else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initViews() {
        views = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        ivDot1 = (ImageView) findViewById(R.id.iv_dot1);
        ivDot1.setEnabled(false);
        ivDot2 = (ImageView) findViewById(R.id.iv_dot2);
        ivDot3 = (ImageView) findViewById(R.id.iv_dot3);

        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View guideFirst = layoutInflater.inflate(R.layout.guide_first, null);
        View guideSecond = layoutInflater.inflate(R.layout.guide_second, null);
        View guideThird = layoutInflater.inflate(R.layout.guide_third, null);

        guideThird.findViewById(R.id.start_home_activity).setOnClickListener(this);

        views.add(guideFirst);
        views.add(guideSecond);
        views.add(guideThird);
    }

    //当页面滑动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //当页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            ivDot1.setEnabled(false);
            ivDot2.setEnabled(true);
            ivDot3.setEnabled(true);
        }else if (position == 1){
            ivDot1.setEnabled(true);
            ivDot2.setEnabled(false);
            ivDot3.setEnabled(true);
        }else if (position == 2){
            ivDot1.setEnabled(true);
            ivDot2.setEnabled(true);
            ivDot3.setEnabled(false);
        }
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_home_activity:

                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isFirstOpen", true);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}