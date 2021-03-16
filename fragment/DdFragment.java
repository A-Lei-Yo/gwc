package com.bawei.dingdan.view.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dingdan.R;
import com.bawei.dingdan.model.bean.JavaBean;
import com.bawei.dingdan.view.adapter.OrderAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class DdFragment extends Fragment {

    @BindView(R.id.tabLayoutId)
    TabLayout tabLayoutId;
    @BindView(R.id.recyclerView_order)
    RecyclerView recyclerViewOrder;
    private Realm realm;
    private Unbinder unbinder;

    private String titles[] = {"全部","代付款","待收货","待评价","已完成"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dd, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTabs();
        initData();

        tabLayoutId.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        recyclerViewOrder.setVisibility(View.VISIBLE);
                        initData();
                        break;

                    case 1:
                        recyclerViewOrder.setVisibility(View.VISIBLE);
                        initData();
                        break;

                        default:
                                recyclerViewOrder.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "暂无数据！！！", Toast.LENGTH_SHORT).show();
                            break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void initData() {
        realm = Realm.getDefaultInstance();
        JavaBean bean = realm.where(JavaBean.class).findFirst();
        if (bean != null && bean.getHomeshops().size()>0){
            Log.i("TAG", "initData: 查询数据库——————》"+bean.getHomeshops().size());
            recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewOrder.setAdapter(new OrderAdapter(getActivity(),bean.getHomeshops()));
        }
    }

    private void initTabs() {
        for (int i = 0; i < titles.length; i++) {
            tabLayoutId.addTab(tabLayoutId.newTab().setText(titles[i]));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
