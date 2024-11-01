package com.example.prm392_fp_soccer_field.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_fp_soccer_field.CallBack.OnYardClickListener;
import com.example.prm392_fp_soccer_field.Models.Yard;
import com.example.prm392_fp_soccer_field.R;

import java.util.List;

public class YardListAdapter extends RecyclerView.Adapter<YardListAdapter.ViewHolder> {

    private List<Yard> yardList;
    private OnYardClickListener listener;

    public YardListAdapter(List<Yard> yardList, OnYardClickListener listener) {
        this.yardList = yardList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public YardListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_soccer_field, parent, false);
        return new YardListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YardListAdapter.ViewHolder holder, int position) {
        Yard field = yardList.get(position);
        holder.field_name.setText(field.getName());
        holder.field_price.setText("Giá: "+String.valueOf(field.price)+" VND");
        holder.field_location.setText("Lưu Hữu Phước Tân Lập, Đông Hoà, Dĩ An, Bình Dương");
        Glide.with(holder.itemView.getContext())
                .load(field.getImg())
                .into(holder.field_image);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onYardClick(field);
            }
        });
    }

    @Override
    public int getItemCount() {
        return yardList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView field_name, field_price, field_location;
        ImageView field_image;

        ViewHolder(View itemView) {
            super(itemView);
            field_name = itemView.findViewById(R.id.field_name);
            field_price = itemView.findViewById(R.id.field_price);
            field_image = itemView.findViewById(R.id.field_image);
            field_location = itemView.findViewById(R.id.field_location);
        }
    }
}
