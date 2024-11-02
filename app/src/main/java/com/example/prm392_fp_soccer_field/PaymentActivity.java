package com.example.prm392_fp_soccer_field;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.prm392_fp_soccer_field.APIs.OrderService;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.Adapters.ChosenServiceAdapter;
import com.example.prm392_fp_soccer_field.Models.AvailableSlot;
import com.example.prm392_fp_soccer_field.Models.CreateOrder;
import com.example.prm392_fp_soccer_field.Models.CreateOrderDetail;
import com.example.prm392_fp_soccer_field.Models.CreatePayment;
import com.example.prm392_fp_soccer_field.Models.Customer;
import com.example.prm392_fp_soccer_field.Models.Order;
import com.example.prm392_fp_soccer_field.Models.OrderDetail;
import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.Models.Yard;
import com.example.prm392_fp_soccer_field.Session.UserSessionManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private ListView itemListView;
    private TextView tvNameCustomerInPayment, tvPhoneCustomerInPayment,
            tvEmailCustomerInPayment, tvCostMustPay, tvTotalPriceServices,
            tvDepositYardInPayment, tvPriceYardInPayment, tvNameYardInPayment,
            tvTimeSlotPayment,titlePayment;
    private Yard yard;
    private List<Service> services;
    private AvailableSlot slot;
    private Customer customer;
    private List<OrderDetail> orderDetails;
    private ChosenServiceAdapter adapter;
    private Button cancelButton, Submit;
    private CreateOrder createOrder;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        init();
        UserSessionManager sessionManager = new UserSessionManager(this);
        createOrder=new CreateOrder();
        if (sessionManager.isLoggedIn()) {
            customer = sessionManager.getUser();
            createOrder.setCustomerId(customer.getId());
        }

        Intent intent = getIntent();
        if (intent != null) {
            orderDetails = intent.getParcelableArrayListExtra("orderDetails");
            slot = intent.getParcelableExtra("slot");
            services = intent.getParcelableArrayListExtra("services");
            yard = intent.getParcelableExtra("yard");
            date = intent.getStringExtra("date");

            if(date!=null){
                titlePayment.setText("Ngày: "+date);
                createOrder.setBookingDate(date);
            }
            createOrder.setDuration(1);
            if (customer != null) {
                tvNameCustomerInPayment.setText("Họ và tên: " + customer.getFirstName() + " " + customer.getLastName());
                tvPhoneCustomerInPayment.setText("Số điện thoại: " + customer.getPhoneNumber());
                tvEmailCustomerInPayment.setText("Email: " + customer.getEmail());
            }

            if (yard != null) {
                tvPriceYardInPayment.setText("Tiền sân: " + yard.getPrice() + " VND");
                tvNameYardInPayment.setText("Tên sân: " + yard.getName());
                createOrder.setYardId(yard.getId());
            }

            if (slot != null) {
                tvTimeSlotPayment.setText("Giờ đặt: " + slot.getStartTime() + " - " + slot.getEndTime());
                createOrder.setSlotId(slot.getSlotId());
            }

            double deposit = (yard != null) ? (yard.getPrice() / 2) : 0;
            tvDepositYardInPayment.setText("Tiền cọc sân: " + deposit + " VND");

            double sum = 0;
            if (orderDetails != null) {
                for (OrderDetail orderDetail : orderDetails) {
                    sum += orderDetail.getTotalPrice();
                }

                List<CreateOrderDetail> createOrderDetails = orderDetails.stream().map(x->{
                    return new CreateOrderDetail(x.getQuantityService(),x.getFinalPrice(),x.getTotalPrice(),x.getServiceId());
                }).collect(Collectors.toList());

                createOrder.setOrderDetails(createOrderDetails);
            }
            tvTotalPriceServices.setText("Tiền dịch vụ: " + sum + " VND");
            double totalCost = (yard != null) ? (yard.getPrice() + sum) : sum;
            tvCostMustPay.setText("Tiền phải trả: " + totalCost / 2 + " VND");
            createOrder.setTotalPrice(totalCost);
            if(orderDetails!=null && services!=null){
                adapter = new ChosenServiceAdapter(this,services,orderDetails);
                itemListView.setAdapter(adapter);
            }
        }

        cancelButton.setOnClickListener(v->finish());
        Submit.setOnClickListener(v->{
            CreatePayment payment = new CreatePayment(createOrder.getTotalPrice(),"Tong","Zalo");
            createOrder.setPayment(payment);
            createOrder.setStatus("Đã thanh toán");
            CallApiPost();
        });
    }

    private void init() {
        titlePayment = findViewById(R.id.titlePayment);
        cancelButton = findViewById(R.id.cancelButton);
        Submit = findViewById(R.id.Submit);
        itemListView = findViewById(R.id.itemListView);
        tvNameCustomerInPayment = findViewById(R.id.tvNameCustomerInPayment);
        tvPhoneCustomerInPayment = findViewById(R.id.tvPhoneCustomerInPayment);
        tvEmailCustomerInPayment = findViewById(R.id.tvEmailCustomerInPayment);
        tvCostMustPay = findViewById(R.id.tvCostMustPay);
        tvTotalPriceServices = findViewById(R.id.tvTotalPriceServices);
        tvDepositYardInPayment = findViewById(R.id.tvDepositYardInPayment);
        tvPriceYardInPayment = findViewById(R.id.tvPriceYardInPayment);
        tvNameYardInPayment = findViewById(R.id.tvNameYardInPayment);
        tvTimeSlotPayment = findViewById(R.id.tvTimeSlotPayment);
    }

    private void CallApiPost() {
        OrderService apiService = RetrofitClient.getClient().create(OrderService.class);
        Call<Order> call = apiService.createOrder(createOrder);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    Order createdOrder = response.body();
                    Toast.makeText(PaymentActivity.this, "Đã thanh toán thành công", Toast.LENGTH_SHORT).show();
                    sendNotification();
                    finish();
                } else {
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        Log.e("PaymentActivity", "Response error: " + errorMessage);
                    } catch (IOException e) {
                        Log.e("PaymentActivity", "Error reading error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable throwable) {
                Log.e("PaymentActivity", "API call failed: " + throwable.getMessage());
            }
        });
    }
    private void sendNotification() {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_id")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Thank you for your order.")
                .setContentText("Your order is accepted. Please to go on time.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    public static class OrderDetailActivity extends Activity {
    }
}
