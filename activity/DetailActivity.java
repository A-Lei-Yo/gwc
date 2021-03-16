package com.bawei.dingdan.view.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bawei.dingdan.R;
import com.bawei.dingdan.model.bean.HomeshopsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.bookname)
    TextView bookname;
    @BindView(R.id.appearance)
    TextView appearance;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.shopinfo)
    TextView shopinfo;

    private Unbinder unbinder;
    private HomeshopsBean homeshopsBean;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        unbinder = ButterKnife.bind(this);

        //获取数据库的实例
        realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.shopCart)
    public void onViewClicked() {
        realm.beginTransaction();
        realm.copyToRealm(homeshopsBean);
        realm.commitTransaction();
        Toast.makeText(this, "添加成功！！！", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receive(HomeshopsBean homeshopsBean){
        this.homeshopsBean = homeshopsBean;
        bookname.setText(homeshopsBean.getBookname());
        appearance.setText(homeshopsBean.getAppearance());
        location.setText(homeshopsBean.getLocation());
        String[] images = homeshopsBean.getImages().split(",");
        image.setImageURI(images[0]);
        shopinfo.setText(homeshopsBean.getShopinfo());
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
