package com.example.prm392_fp_soccer_field.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private List<Service> services;

    public ServiceAdapter(List<Service> services) {
        this.services = services;
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        Service service = services.get(position);

        holder.tvNameService.setText(service.getName());
        holder.tvServicePrice.setText("Giá: " + service.getPrice() + " VND");
        holder.tvDescriptionService.setText(service.getDescription());

        Glide.with(holder.itemView.getContext())
                .load(service.getImg())
                .override(holder.ivServiceImage.getWidth(), holder.ivServiceImage.getHeight())
                .into(holder.ivServiceImage);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameService, tvServicePrice, tvDescriptionService;
        ImageView ivServiceImage;

        ViewHolder(View itemView) {
            super(itemView);
            tvNameService = itemView.findViewById(R.id.tvNameService);
            tvServicePrice = itemView.findViewById(R.id.tvServicePrice);
            tvDescriptionService = itemView.findViewById(R.id.tvDescriptionService);
            ivServiceImage = itemView.findViewById(R.id.ivServiceImage);
        }
    }
}
