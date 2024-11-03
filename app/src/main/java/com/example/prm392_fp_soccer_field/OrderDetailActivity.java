package com.example.prm392_fp_soccer_field;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prm392_fp_soccer_field.APIs.OrderDetailService;
import com.example.prm392_fp_soccer_field.APIs.OrderService;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.APIs.ServiceService;
import com.example.prm392_fp_soccer_field.APIs.YardService;
import com.example.prm392_fp_soccer_field.Adapters.ServiceAdapter;
import com.example.prm392_fp_soccer_field.Models.Order;
import com.example.prm392_fp_soccer_field.Models.OrderDetail;
import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.Models.TableRowService;
import com.example.prm392_fp_soccer_field.Models.Yard;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends Activity {
    private ImageView ivYardImageOrderDetail;
    private TextView tvNameYardInOrderDetail,tvStartTimeInOrderDetail,tvEndTimeInOrderDetail,
            tvDateBookingInOrderDetail,titleYardPriceOrderDetail,titleTotalOrderDetail,titlePaymentInOrderDetail;
    private Button tvOrderStatusInOrderDetail, btnBackInOrderDetail;
    private TableLayout tableServices;
    private List<TableRowService> tableRowService;
    private List<Service> services;
    private List<OrderDetail> orderDetails;
    private Yard yard;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_activity);
        init();
        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId",-1);

        if(orderId!=-1){
            CallOrderApi(orderId);
            setupOrderDetails(orderId);
        }

        btnBackInOrderDetail.setOnClickListener(v->finish());

    }
    private void setUpOrderInformation(){
        tvStartTimeInOrderDetail.setText("Giờ bắt đầu: "+order.getStartTime());
        tvEndTimeInOrderDetail.setText("Giờ kết thúc: "+order.getEndTime());
        tvDateBookingInOrderDetail.setText(order.getBookingDate());
        tvOrderStatusInOrderDetail.setText(order.getStatus());

        titlePaymentInOrderDetail.setText("Tiền đã trả: "+order.getTotalPrice()+ " VND");
    }
    private void setUpYardInformation(){
        Glide.with(this)
                .load(yard.getImg())
                .into(ivYardImageOrderDetail);

        tvNameYardInOrderDetail.setText("Sân "+yard.getName());
        titleYardPriceOrderDetail.setText("Giá sân: "+yard.getPrice()+ " VND");
    }
    private void init(){
        ivYardImageOrderDetail = findViewById(R.id.ivYardImageOrderDetail);
        tvNameYardInOrderDetail= findViewById(R.id.tvNameYardInOrderDetail);
        tvStartTimeInOrderDetail=findViewById(R.id.tvStartTimeInOrderDetail);
        tvEndTimeInOrderDetail = findViewById(R.id.tvEndTimeInOrderDetail);
        tvDateBookingInOrderDetail = findViewById(R.id.tvDateBookingInOrderDetail);
        tvOrderStatusInOrderDetail = findViewById(R.id.tvOrderStatusInOrderDetail);
        titleYardPriceOrderDetail = findViewById(R.id.titleYardPriceOrderDetail);
        titleTotalOrderDetail = findViewById(R.id.titleTotalOrderDetail);
        titlePaymentInOrderDetail = findViewById(R.id.titlePaymentInOrderDetail);
        tableServices= findViewById(R.id.tableServices);
        btnBackInOrderDetail = findViewById(R.id.btnBackInOrderDetail);


    }

    private void CallYarApi(int id){
        YardService apiService = RetrofitClient.getClient().create(YardService.class);

        Call<Yard> call = apiService.getYardById(id);
        call.enqueue(new Callback<Yard>() {
            @Override
            public void onResponse(Call<Yard> call, Response<Yard> response) {
                if (response.isSuccessful()) {
                    yard = response.body();
                    setUpYardInformation();
                }

            }

            @Override
            public void onFailure(Call<Yard> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }
    private void CallOrderApi(int id){
        OrderService apiService = RetrofitClient.getClient().create(OrderService.class);


        Call<Order> call = apiService.getOrderById(id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    order = response.body();
                    setUpOrderInformation();
                    CallYarApi(order.getYardId());

                }

            }

            @Override
            public void onFailure(Call<Order> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }
    private void setupServices() {
        ServiceService apiService = RetrofitClient.getClient().create(ServiceService.class);
        Call<List<Service>> call = apiService.getAllServices();
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful()) {
                    services = response.body();
                    setUpTableRow();
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }


    private void setupOrderDetails(int id) {
        OrderDetailService apiService = RetrofitClient.getClient().create(OrderDetailService.class);
        Call<List<OrderDetail>> call = apiService.getOrderDetailsByOrderId(id);
        call.enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                if (response.isSuccessful()) {
                    orderDetails = response.body();
                    setupServices();
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }
    private void setSumTotalService(){
        double sum = tableRowService.stream()
                .mapToDouble(x -> x.getTotal())
                .sum();
        titleTotalOrderDetail.setText("Giá dịch vụ: " + sum + " VND");
    }
    private void setUpTableRow() {
        if (orderDetails != null && services != null) {
            tableRowService = orderDetails.stream().map(orderDetail -> {
                Service service = services.stream()
                        .filter(s -> s.getId() == orderDetail.getServiceId())
                        .findFirst()
                        .orElse(null);
                return new TableRowService(
                        service != null ? service.getName() : "Unknown",
                        orderDetail.getFinalPrice(),
                        orderDetail.getQuantityService(),
                        orderDetail.getTotalPrice()
                );
            }).collect(Collectors.toList());

            for (TableRowService rowService : tableRowService) {
                TableRow tableRow = new TableRow(this);

                TextView serviceName = new TextView(this);
                serviceName.setText(rowService.getName());

                TextView quantity = new TextView(this);
                quantity.setText(String.valueOf(rowService.getQuantity()));


                TextView unitPrice = new TextView(this);
                unitPrice.setText(String.valueOf(rowService.getPrice())+ " VND");


                TextView totalPrice = new TextView(this);
                totalPrice.setText(String.valueOf(rowService.getTotal() + " VND"));


                tableRow.addView(serviceName);
                tableRow.addView(quantity);
                tableRow.addView(unitPrice);
                tableRow.addView(totalPrice);

                tableServices.addView(tableRow);

                setSumTotalService();
            }
        }
    }


}
