package com.bawei.dingdan.view.activity;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bawei.dingdan.R;
import com.bawei.dingdan.view.fragment.DdFragment;
import com.bawei.dingdan.view.fragment.FlFragment;
import com.bawei.dingdan.view.fragment.GwcFragment;
import com.bawei.dingdan.view.fragment.SyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager_id)
    ViewPager viewPagerId;
    @BindView(R.id.radioGroup_id)
    RadioGroup radioGroupId;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragments = new ArrayList<>();
        fragments.add(new SyFragment());
        fragments.add(new FlFragment());
        fragments.add(new GwcFragment());
        fragments.add(new DdFragment());

        viewPagerId.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });


        viewPagerId.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                radioGroupId.check(radioGroupId.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        radioGroupId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewPagerId.setCurrentItem(radioGroupId.indexOfChild(radioGroupId.findViewById(checkedId)));
            }
        });


    }

}
