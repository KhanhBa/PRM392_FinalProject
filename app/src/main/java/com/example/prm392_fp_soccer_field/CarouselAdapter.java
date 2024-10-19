package com.example.prm392_fp_soccer_field;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.carousel.MaskableFrameLayout;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<Integer> items;

    public CarouselAdapter(List<Integer> items) {
        this.items = items;
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        public MaskableFrameLayout container;
        public ImageView imageView;

        public CarouselViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.carousel_item_container);
            imageView = itemView.findViewById(R.id.carousel_image_view);
        }
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.imageView.setImageResource(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}