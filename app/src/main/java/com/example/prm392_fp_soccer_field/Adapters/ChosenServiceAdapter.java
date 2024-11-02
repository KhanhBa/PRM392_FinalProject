package com.example.prm392_fp_soccer_field.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prm392_fp_soccer_field.Models.OrderDetail;
import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.R;

import java.util.List;
import java.util.Optional;

public class ChosenServiceAdapter extends BaseAdapter {
    private Context context;
    private List<Service> services;
    private List<OrderDetail> orderDetails;

    public ChosenServiceAdapter(Context context, List<Service> services, List<OrderDetail> orderDetails) {
        this.context = context;
        this.services = services;
        this.orderDetails = orderDetails;
    }

    @Override
    public int getCount() {
        return orderDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return orderDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_service_in_payment, parent, false);
            holder = new ViewHolder();
            holder.serviceNameInPayment = convertView.findViewById(R.id.serviceNameInPayment);
            holder.servicePriceInPayment = convertView.findViewById(R.id.servicePriceInPayment);
            holder.serviceQuantityInPayment = convertView.findViewById(R.id.serviceQuantityInPayment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OrderDetail orderDetail = orderDetails.get(position);

        // Find the service based on service ID in orderDetail
        Optional<Service> serviceOptional = services.stream()
                .filter(service -> service.getId() == orderDetail.getServiceId())
                .findFirst();

        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();
            holder.serviceNameInPayment.setText(service.getName());
            holder.servicePriceInPayment.setText("Giá: " + service.getPrice() + " VND");
        } else {
            holder.serviceNameInPayment.setText("Dịch vụ không tồn tại");
            holder.servicePriceInPayment.setText("Giá: Không xác định");
        }

        holder.serviceQuantityInPayment.setText("Số lượng: " + orderDetail.getQuantityService());

        return convertView;
    }

    private static class ViewHolder {
        TextView serviceNameInPayment;
        TextView servicePriceInPayment;
        TextView serviceQuantityInPayment;
    }
}
