package com.example.prm392_fp_soccer_field;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.APIs.ServiceService;
import com.example.prm392_fp_soccer_field.APIs.SlotService;
import com.example.prm392_fp_soccer_field.Adapters.ChooseServiceAdapter;
import com.example.prm392_fp_soccer_field.CallBack.OnBookServiceClick;
import com.example.prm392_fp_soccer_field.Models.AvailableSlot;
import com.example.prm392_fp_soccer_field.Models.OrderDetail;
import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.Models.Yard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    private TextView titleTextView;
    private Button backButton;
    private Button continueButton;
    private List<AvailableSlot> slots;
    private Yard yard;
    private List<Service> services;
    private RadioGroup slotGroup;
    private RecyclerView rc;
    private AvailableSlot selectedSlot;
    private List<OrderDetail> orderDetails;
    private ChooseServiceAdapter adapter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        orderDetails = new ArrayList<>();
        titleTextView = findViewById(R.id.titleTextView);
        backButton = findViewById(R.id.buttonBackInBook);
        continueButton = findViewById(R.id.buttonContinueInBook);
        slotGroup = findViewById(R.id.radioGroupSlots);
        rc = findViewById(R.id.recyclerViewServices);
        rc.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        Yard yard = intent.getParcelableExtra("yard");
        String date = intent.getStringExtra("date");
        this.date = date;

        titleTextView.setText("ĐẶT SÂN");

        if (yard != null && date != null) {
            fetchAvailableSlots(date, yard.getId());
            fetchServices();
            this.yard=yard;
        }

        backButton.setOnClickListener(v -> finish());

        continueButton.setOnClickListener(v -> {
            if (selectedSlot == null) {
                Toast.makeText(this, "You need to choose a slot to continue.", Toast.LENGTH_SHORT).show();
            } else {
                Intent newIntent = new Intent(BookActivity.this, PaymentActivity.class);
                newIntent.putParcelableArrayListExtra("orderDetails", (ArrayList<? extends Parcelable>) orderDetails.stream().filter(x->x.getQuantityService()!=0).collect(Collectors.toList()));
                newIntent.putExtra("slot",selectedSlot);
                newIntent.putParcelableArrayListExtra("services", (ArrayList<? extends Parcelable>) services);
                newIntent.putExtra("yard",yard);
                newIntent.putExtra("date",date);
                startActivity(newIntent);
                finish();
            }
        });

        slotGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                String selectedText = radioButton.getText().toString();
                selectedSlot = slots.stream()
                        .filter(slot -> (slot.getStartTime() + " - " + slot.getEndTime()).equals(selectedText))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void fetchAvailableSlots(String date, int yardId) {
        SlotService apiService = RetrofitClient.getClient().create(SlotService.class);
        Call<List<AvailableSlot>> call = apiService.getAllAvailableSlots(yardId, date);
        call.enqueue(new Callback<List<AvailableSlot>>() {
            @Override
            public void onResponse(Call<List<AvailableSlot>> call, Response<List<AvailableSlot>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    slots = response.body().stream()
                            .filter(slot -> "Trống".equals(slot.getStatus()))
                            .collect(Collectors.toList());
                    populateSlotChoices();
                } else {
                    Log.e("BookActivity", "No available slots found.");
                }
            }

            @Override
            public void onFailure(Call<List<AvailableSlot>> call, Throwable throwable) {
                Log.e("BookActivity", "API call failed: " + throwable.getMessage());
            }
        });
    }

    private void populateSlotChoices() {
        if (slots != null && !slots.isEmpty()) {
            for (AvailableSlot slot : slots) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(slot.getStartTime() + " - " + slot.getEndTime());
                slotGroup.addView(radioButton);
            }
        }
    }

    private void fetchServices() {
        ServiceService apiService = RetrofitClient.getClient().create(ServiceService.class);
        Call<List<Service>> call = apiService.getAllServices();
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    services = response.body();
                    orderDetails = services.stream()
                            .map(service -> new OrderDetail(0, 0, 0, service.getPrice(), 0, service.getId()))
                            .collect(Collectors.toList());

                    adapter = new ChooseServiceAdapter(services, orderDetails, new OnBookServiceClick() {
                        @Override
                        public void onBookServiceClick(OrderDetail orderDetail) {
                            OrderDetail data = orderDetails.stream().filter(x -> x.getServiceId() == orderDetail.getServiceId()).findAny().get();
                            data.setQuantityService(orderDetail.getQuantityService());
                            data.setTotalPrice(orderDetail.getTotalPrice());
                        }

                        @Override
                        public void offBookServiceClick(OrderDetail orderDetail) {
                            OrderDetail data = orderDetails.stream().filter(x -> x.getServiceId() == orderDetail.getServiceId()).findAny().get();
                            data.setQuantityService(0);
                            data.setTotalPrice(0);
                        }
                    });
                    rc.setAdapter(adapter);
                } else {
                    Log.e("BookActivity", "Không tìm thấy dịch vụ nào.");
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable throwable) {
                Log.e("BookActivity", "Gọi API thất bại: " + throwable.getMessage());
            }
        });
    }

}
