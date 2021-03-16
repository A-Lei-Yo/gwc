package com.bawei.dingdan.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dingdan.R;
import com.bawei.dingdan.model.bean.HomeshopsBean;
import com.bawei.dingdan.model.utils.ImageUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * 项目名称：
 *
 * @Author 张亚磊
 * 创建时间：2021/3/12 9:30
 */
public class GwcAdapter extends RecyclerView.Adapter<GwcAdapter.ViewHolder> {


    private Context context;
    private List<HomeshopsBean> homeshopsBeans;
    private Realm realm;

    public GwcAdapter(Context context, List<HomeshopsBean> homeshopsBeans) {
        this.context = context;
        this.homeshopsBeans = homeshopsBeans;
        realm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.gwc_item, parent, false));
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

        //同步状态
        holder.checkBoxUsername.setChecked(bean.isSelected());
        holder.checkboxBook.setChecked(bean.isSelected());

        holder.checkBoxUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                boolean checked = holder.checkBoxUsername.isChecked();
                bean.setSelected(checked);
                holder.checkBoxUsername.setChecked(checked);
                EventBus.getDefault().post("jisuan");
                realm.commitTransaction();
            }
        });


        holder.checkboxBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                boolean checked = holder.checkboxBook.isChecked();
                bean.setSelected(checked);
                holder.checkboxBook.setChecked(checked);
                EventBus.getDefault().post("jisuan");
                realm.commitTransaction();
            }
        });



    }

    @Override
    public int getItemCount() {
        return homeshopsBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkBox_username)
        CheckBox checkBoxUsername;
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.checkbox_book)
        CheckBox checkboxBook;
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
