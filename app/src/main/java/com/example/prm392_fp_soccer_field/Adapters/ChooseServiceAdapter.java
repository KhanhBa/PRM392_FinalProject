package com.example.prm392_fp_soccer_field.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_fp_soccer_field.CallBack.OnBookServiceClick;
import com.example.prm392_fp_soccer_field.Models.OrderDetail;
import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.R;

import java.util.List;
import java.util.Optional;

public class ChooseServiceAdapter extends RecyclerView.Adapter<ChooseServiceAdapter.ViewHolder> {

    private List<Service> services;
    private List<OrderDetail> orderDetails;
    private OnBookServiceClick click;

    public ChooseServiceAdapter(List<Service> services, List<OrderDetail> orderDetails, OnBookServiceClick click) {
        this.services = services;
        this.orderDetails = orderDetails;
        this.click = click;
    }

    @NonNull
    @Override
    public ChooseServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_add_service, parent, false);
        return new ChooseServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseServiceAdapter.ViewHolder holder, int position) {
        Service service = services.get(position);

        Optional<OrderDetail> optionalOrderDetail = orderDetails.stream()
                .filter(orderDetail -> orderDetail.getServiceId() == service.getId())
                .findFirst();

        holder.serviceName.setText(service.getName());
        holder.servicePrice.setText("Giá: " + service.getPrice() + " VND");
        holder.checkBoxService.setChecked(optionalOrderDetail.isPresent());

        // Nếu dịch vụ đã được chọn, cập nhật số lượng
        holder.editTextQuantity.setText(optionalOrderDetail.map(orderDetail -> String.valueOf(orderDetail.getQuantityService())).orElse(""));

        holder.editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.checkBoxService.isChecked()) {
                    optionalOrderDetail.ifPresent(orderDetail -> {
                        try {
                            int quantity = Integer.parseInt(s.toString());
                            if (quantity >= 1) {
                                orderDetail.setQuantityService(quantity);
                                orderDetail.setTotalPrice(orderDetail.getFinalPrice() * orderDetail.getQuantityService());
                                click.onBookServiceClick(orderDetail);
                            } else {
                                holder.editTextQuantity.setError("Số lượng tối thiểu là 1");
                            }
                        } catch (NumberFormatException e) {
                            holder.editTextQuantity.setError("Vui lòng nhập số hợp lệ");
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        holder.checkBoxService.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                optionalOrderDetail.ifPresent(orderDetail -> {
                    holder.editTextQuantity.setText("1");
                    orderDetail.setQuantityService(1);
                    orderDetail.setTotalPrice(orderDetail.getFinalPrice());
                    click.onBookServiceClick(orderDetail);
                });
            } else {
                optionalOrderDetail.ifPresent(orderDetail -> {
                    holder.editTextQuantity.setText("");
                    orderDetail.setQuantityService(0);
                    orderDetail.setTotalPrice(0);
                    click.offBookServiceClick(orderDetail);
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return services.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName, servicePrice;
        EditText editTextQuantity;
        CheckBox checkBoxService;

        ViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            servicePrice = itemView.findViewById(R.id.servicePrice);
            editTextQuantity = itemView.findViewById(R.id.editTextQuantity);
            checkBoxService = itemView.findViewById(R.id.checkBoxService);
        }
    }
}

