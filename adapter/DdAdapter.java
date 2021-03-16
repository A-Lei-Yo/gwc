package com.bawei.dingdan.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dingdan.R;
import com.bawei.dingdan.model.bean.HomeshopsBean;
import com.bawei.dingdan.model.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：
 *
 * @Author 张亚磊
 * 创建时间：2021/3/12 10:43
 */
public class DdAdapter extends RecyclerView.Adapter<DdAdapter.ViewHolder> {


    private Context context;
    private List<HomeshopsBean> homeshopsBeans;

    public DdAdapter(Context context, List<HomeshopsBean> homeshopsBeans) {
        this.context = context;
        this.homeshopsBeans = homeshopsBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.confirm_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeshopsBean bean = homeshopsBeans.get(position);
        holder.username.setText(bean.getUser().getUsername());
        holder.bookname.setText(bean.getBookname());
        holder.appearance.setText(bean.getAppearance());
        holder.location.setText(bean.getLocation());
        holder.nowprice.setText("￥"+bean.getNowprice());

        String[] split = bean.getImages().split(",");
        ImageUtils.getImageUtils().loadImage(split[0],holder.bookImage);
    }

    @Override
    public int getItemCount() {
        return homeshopsBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.bookImage)
        ImageView bookImage;
        @BindView(R.id.bookname)
        TextView bookname;
        @BindView(R.id.appearance)
        TextView appearance;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.nowprice)
        TextView nowprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
