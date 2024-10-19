package com.example.prm392_fp_soccer_field;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SoccerFieldAdapter extends RecyclerView.Adapter<SoccerFieldAdapter.ViewHolder> {

    private List<SoccerField> fields;
    private OnFieldClickListener listener;

    public interface OnFieldClickListener {
        void onFieldClick(SoccerField field);
    }

    public SoccerFieldAdapter(List<SoccerField> fields, OnFieldClickListener listener) {
        this.fields = fields;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_soccer_field, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SoccerField field = fields.get(position);
        holder.fieldName.setText(field.getName());
        holder.fieldLocation.setText(field.getLocation());
        holder.fieldPrice.setText(field.getPrice());
        holder.fieldImage.setImageResource(field.getImageResourceId());
        holder.fieldRating.setRating(field.getRating());

        holder.bookButton.setOnClickListener(v -> listener.onFieldClick(field));
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fieldImage;
        TextView fieldName, fieldLocation, fieldPrice;
        Button bookButton;
        RatingBar fieldRating;

        ViewHolder(View itemView) {
            super(itemView);
            fieldImage = itemView.findViewById(R.id.field_image);
            fieldName = itemView.findViewById(R.id.field_name);
            fieldLocation = itemView.findViewById(R.id.field_location);
            fieldPrice = itemView.findViewById(R.id.field_price);
            bookButton = itemView.findViewById(R.id.book_button);
            fieldRating = itemView.findViewById(R.id.field_rating);
        }
    }
}