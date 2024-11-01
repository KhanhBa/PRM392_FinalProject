package com.example.prm392_fp_soccer_field.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView; // Ensure CardView is imported

import com.example.prm392_fp_soccer_field.Models.AvailableSlot;
import com.example.prm392_fp_soccer_field.R;

import java.util.List;

public class AvailableAdapter extends ArrayAdapter<AvailableSlot> {

    public AvailableAdapter(Context context, List<AvailableSlot> slotList) {
        super(context, 0, slotList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if we are using a recycled view
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.item_available_slot, parent, false);
        }

        AvailableSlot currentSlot = getItem(position);

        TextView tvNameAvailableSlot = listItem.findViewById(R.id.tvNameAvailableSlot);
        TextView tvTimeSlot = listItem.findViewById(R.id.tvTimeSlot);
        TextView tvStatus = listItem.findViewById(R.id.tvStatus);
        CardView cardView = listItem.findViewById(R.id.cardView); // Ensure you have a CardView in item_slot layout

        if (currentSlot != null) {
            tvNameAvailableSlot.setText(currentSlot.getCustomerName());
            tvTimeSlot.setText(currentSlot.getStartTime()+ " - "+currentSlot.getEndTime());
            tvStatus.setText(currentSlot.getStatus());

            if ("Trống".equals(currentSlot.getStatus())) {
                tvStatus.setBackgroundColor(getContext().getResources().getColor(R.color.green));
            } else {
                tvStatus.setBackgroundColor(getContext().getResources().getColor(R.color.red));
            }
        }

        return listItem;
    }
}
