package com.bawei.dingdan.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dingdan.R;
import com.bawei.dingdan.model.bean.HomeshopsBean;
import com.bawei.dingdan.model.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * 项目名称：
 *
 * @Author 张亚磊
 * 创建时间：2021/3/12 11:33
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<HomeshopsBean> homeshopsBeans;
    private Realm realm;

    public OrderAdapter(Context context, List<HomeshopsBean> homeshopsBeans) {
        this.context = context;
        this.homeshopsBeans = homeshopsBeans;
        realm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.dd_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeshopsBean beans = homeshopsBeans.get(position);
        holder.username.setText(beans.getUser().getUsername());
        holder.bookname.setText(beans.getBookname());
        holder.appearance.setText(beans.getAppearance());
        holder.location.setText(beans.getLocation());
        holder.nowprice.setText(beans.getNowprice()+"");

        String[] split = beans.getImages().split(",");
        ImageUtils.getImageUtils().loadImage(split[0],holder.bookImage);

        holder.deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();

                homeshopsBeans.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "删除成功！！！", Toast.LENGTH_SHORT).show();
                realm.commitTransaction();
            }
        });
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
        @BindView(R.id.deleteOrder)
        TextView deleteOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
