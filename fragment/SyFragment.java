package com.bawei.dingdan.view.fragment;


import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dingdan.R;
import com.bawei.dingdan.base.BaseFragment;
import com.bawei.dingdan.contract.ShoppingContract;
import com.bawei.dingdan.model.bean.JavaBean;
import com.bawei.dingdan.model.utils.NetUtils;
import com.bawei.dingdan.presenter.ShoppingPresenter;
import com.bawei.dingdan.view.adapter.SyAdapter;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SyFragment extends BaseFragment<ShoppingPresenter> implements ShoppingContract.ShopContractView {

    @BindView(R.id.recycler_id)
    RecyclerView recyclerId;
    private SyAdapter syAdapter;

    @Override
    protected void initData() {
        if (NetUtils.hasNetWorkConn(getActivity())) {
            mPresenter.getShopData();
        } else {
            Toast.makeText(getActivity(), "无网络连接！！！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected ShoppingPresenter initPresenter() {
        return new ShoppingPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_sy;
    }

    @Override
    public void onSuccess(JavaBean javaBean) {

        recyclerId.setLayoutManager(new GridLayoutManager(getActivity(),2));
        syAdapter = new SyAdapter(getActivity(),javaBean.getHomeshops());
        recyclerId.setAdapter(syAdapter);



    }
}
