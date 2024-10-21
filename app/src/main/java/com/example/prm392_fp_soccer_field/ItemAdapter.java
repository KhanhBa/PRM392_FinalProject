package com.example.prm392_fp_soccer_field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private Item[] items;

    public ItemAdapter(Context context, Item[] items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        CheckBox itemCheckbox = convertView.findViewById(R.id.itemCheckbox);
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView quantityText = convertView.findViewById(R.id.quantityText);
        ImageButton decreaseButton = (ImageButton) convertView.findViewById(R.id.decreaseButton);
        ImageButton increaseButton = (ImageButton) convertView.findViewById(R.id.increaseButton);



        // Hiển thị tên item
        itemName.setText(items[position].getName());
        quantityText.setText(String.valueOf(items[position].getQuantity()));

        // Tăng số lượng
        increaseButton.setOnClickListener(v -> {
            items[position].setQuantity(items[position].getQuantity() + 1);
            quantityText.setText(String.valueOf(items[position].getQuantity()));
        });

        // Giảm số lượng
        decreaseButton.setOnClickListener(v -> {
            if (items[position].getQuantity() > 0) {
                items[position].setQuantity(items[position].getQuantity() - 1);
                quantityText.setText(String.valueOf(items[position].getQuantity()));
            }
        });

        return convertView;
    }
}

