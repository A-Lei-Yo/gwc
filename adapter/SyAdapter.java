package com.bawei.dingdan.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dingdan.R;
import com.bawei.dingdan.model.bean.HomeshopsBean;
import com.bawei.dingdan.view.activity.DetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：
 *
 * @Author 张亚磊
 * 创建时间：2021/3/10 10:39
 */
public class SyAdapter extends RecyclerView.Adapter<SyAdapter.ViewHolder> {



    private Context context;
    private List<HomeshopsBean> data;

    public SyAdapter(Context context, List<HomeshopsBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.syName.setText(data.get(position).getBookname());
        holder.syPrice.setText("￥" + data.get(position).getNowprice());
        holder.appearance.setText(data.get(position).getAppearance());
        holder.location.setText(data.get(position).getLocation());

        String[] images = data.get(position).getImages().split(",");
        holder.sdv.setImageURI(images[0]);

        //点击条目跳转到详情页面
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(data.get(position));
                Intent intent = new Intent(context,DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv)
        SimpleDraweeView sdv;
        @BindView(R.id.sy_name)
        TextView syName;
        @BindView(R.id.sy_price)
        TextView syPrice;
        @BindView(R.id.appearance)
        TextView appearance;
        @BindView(R.id.location)
        TextView location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
