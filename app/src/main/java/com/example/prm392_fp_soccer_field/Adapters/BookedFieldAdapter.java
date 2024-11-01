package com.example.prm392_fp_soccer_field.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_fp_soccer_field.Models.BookedField;
import com.example.prm392_fp_soccer_field.R;

import java.util.List;

public class BookedFieldAdapter extends RecyclerView.Adapter<BookedFieldAdapter.ViewHolder> {

    private List<BookedField> bookedFields;

    public BookedFieldAdapter(List<BookedField> bookedFields) {
        this.bookedFields = bookedFields;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked_field, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookedField field = bookedFields.get(position);
        holder.fieldName.setText(field.getFieldName());
        holder.bookingDate.setText(field.getDate());
        holder.bookingTime.setText(field.getTime());
        holder.bookingStatus.setText(field.getStatus());

        holder.cancelBooking.setOnClickListener(v -> {
            // Handle cancellation here
        });
    }

    @Override
    public int getItemCount() {
        return bookedFields.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fieldName, bookingDate, bookingTime, bookingStatus;
        Button cancelBooking;

        ViewHolder(View itemView) {
            super(itemView);
            fieldName = itemView.findViewById(R.id.field_name);
            bookingDate = itemView.findViewById(R.id.booking_date);
            bookingTime = itemView.findViewById(R.id.booking_time);
            bookingStatus = itemView.findViewById(R.id.booking_status);
            cancelBooking = itemView.findViewById(R.id.cancel_booking);
        }
    }
}