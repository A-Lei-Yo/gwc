package com.bawei.dingdan.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dingdan.R;
import com.bawei.dingdan.model.bean.HomeshopsBean;
import com.bawei.dingdan.model.bean.JavaBean;
import com.bawei.dingdan.view.adapter.DdAdapter;

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
import io.realm.RealmList;

public class ConfirmOrderActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_confirmOrder)
    RecyclerView recyclerViewConfirmOrder;
    @BindView(R.id.totalPrice)
    TextView tv_totalPrice;
    @BindView(R.id.confirmOrder)
    Button confirmOrder;

    private int totalPrice;
    private List<HomeshopsBean> beans;

    private Unbinder unbinder;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        unbinder = ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        recyclerViewConfirmOrder.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.confirmOrder)
    public void onViewClicked() {
        realm.beginTransaction();

        RealmList<HomeshopsBean> realmList = new RealmList<>();

        for (int i = 0; i < beans.size(); i++) {
            realmList.add(beans.get(i));
        }
        JavaBean javaBean = new JavaBean();
        javaBean.setHomeshops(realmList);

        realm.copyToRealm(javaBean);
        realm.commitTransaction();

        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receive(List<HomeshopsBean> beans){
        this.beans = beans;
        DdAdapter ddAdapter = new DdAdapter(this,beans);
        recyclerViewConfirmOrder.setAdapter(ddAdapter);
        for (int i = 0; i < beans.size(); i++) {
            totalPrice += beans.get(i).getNowprice();
        }
        tv_totalPrice.setText("共计："+totalPrice);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
