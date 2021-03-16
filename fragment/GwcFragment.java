package com.bawei.dingdan.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dingdan.R;
import com.bawei.dingdan.model.bean.HomeshopsBean;
import com.bawei.dingdan.view.activity.ConfirmOrderActivity;
import com.bawei.dingdan.view.adapter.GwcAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class GwcFragment extends Fragment {

    @BindView(R.id.recyclerView_shopCart)
    RecyclerView recyclerViewShopCart;
    @BindView(R.id.cb_select_all)
    CheckBox cbSelectAll;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_total_count)
    TextView tvTotalCount;
    private Unbinder unbinder;

    private RealmResults<HomeshopsBean> beans;

    private List<HomeshopsBean> homeshopsBeans = new ArrayList<>();

    private GwcAdapter adapter;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gwc, container, false);
        unbinder = ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerViewShopCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        beans = realm.where(HomeshopsBean.class).findAll();
        adapter = new GwcAdapter(getActivity(), this.beans);
        recyclerViewShopCart.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    @OnClick(R.id.cb_select_all)
    public void onViewClicked() {
        realm.beginTransaction();
        boolean checked = cbSelectAll.isChecked();
        for (int i = 0; i < beans.size(); i++) {
            beans.get(i).setSelected(checked);
        }
        num();
        realm.commitTransaction();
    }

    private int price;
    private int count;
    private boolean isSelected;

    private void num() {
        price = 0;
        count = 0;
        isSelected = true;

        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).isSelected()) {
                price += beans.get(i).getNowprice();
                count += 1;
            } else {
                isSelected = false;
            }
        }
        adapter.notifyDataSetChanged();
        tvTotalCount.setText("共计：" + count + "件商品");
        tvTotalPrice.setText("总价：" + price);
        cbSelectAll.setChecked(isSelected);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getEvent(String s) {
        if ("jisuan".equals(s)) {
            num();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.buy)
    public void buy() {
        homeshopsBeans.clear();
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).isSelected()){
                homeshopsBeans.add(beans.get(i));
            }
        }
        EventBus.getDefault().postSticky(homeshopsBeans);
        Intent intent = new Intent(getActivity(),ConfirmOrderActivity.class);
        startActivity(intent);
    }
}
