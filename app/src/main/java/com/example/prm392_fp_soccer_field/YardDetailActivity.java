package com.example.prm392_fp_soccer_field;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.APIs.SlotService;
import com.example.prm392_fp_soccer_field.APIs.YardService;
import com.example.prm392_fp_soccer_field.Adapters.AvailableAdapter;
import com.example.prm392_fp_soccer_field.Models.AvailableSlot;
import com.example.prm392_fp_soccer_field.Models.Yard;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YardDetailActivity  extends AppCompatActivity {
    private Button btnShowDatePicker, btnBack;
    private DatePicker datePicker;
    private ImageView ivYardImage;
    private TextView tvSelectedDate, tvNameInYardDetail, tvPriceInYardDetail, tvDescriptionInYardDetail, tvStatusInYardDetail;
    private Yard yard;
    private ListView recyclerViewTimeSlots;
    private AvailableAdapter slotAdapter;
    private List<AvailableSlot> slots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_yard_activity);
        init();

        Intent intent = getIntent();
        int yardId =  intent.getIntExtra("yard", 0);

        if(yardId!=0) CallApi(yardId);

        btnShowDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePicker.getVisibility() == View.GONE) {
                    datePicker.setVisibility(View.VISIBLE);
                    tvSelectedDate.setVisibility(View.VISIBLE);
                } else {
                    datePicker.setVisibility(View.GONE);
                }
            }
        });
        Button btnBackInYardDetail = findViewById(R.id.btnBackInYardDetail);

        btnBackInYardDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YardDetailActivity.this, "Oke", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                tvSelectedDate.setText("Ngày đã chọn: " + selectedDate);
                String upDate = year +"-" + (monthOfYear+1) +"-" + dayOfMonth;
                CallAvailableSlotApi(upDate);
            }
        });
    }
    private void CallAvailableSlotApi(String day){
        SlotService apiService = RetrofitClient.getClient().create(SlotService.class);

        Call<List<AvailableSlot>> call = apiService.getAllAvailableSlots(yard.getId(),day);
        call.enqueue(new Callback<List<AvailableSlot>>() {
            @Override
            public void onResponse(Call<List<AvailableSlot>> call, Response<List<AvailableSlot>> response) {
                if (response.isSuccessful()) {
                    List<AvailableSlot> data = response.body();
                    if (data != null) {
                        slotAdapter = new AvailableAdapter(YardDetailActivity.this,data);
                        recyclerViewTimeSlots.setAdapter(slotAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<AvailableSlot>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }
    public void init(){
        tvNameInYardDetail = findViewById(R.id.tvNameInYardDetail);
        tvPriceInYardDetail = findViewById(R.id.tvPriceInYardDetail);
        tvDescriptionInYardDetail = findViewById(R.id.tvDescriptionInYardDetail);
        tvStatusInYardDetail = findViewById(R.id.tvStatusInYardDetail);
        btnShowDatePicker = findViewById(R.id.btnShowDatePicker);
        datePicker = findViewById(R.id.datePicker);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        ivYardImage=findViewById(R.id.ivYardImage);
        recyclerViewTimeSlots = findViewById(R.id.recyclerViewTimeSlots);
    }

    private void CallApi(int id){
        YardService apiService = RetrofitClient.getClient().create(YardService.class);


        Call<Yard> call = apiService.getYardById(id);
        call.enqueue(new Callback<Yard>() {
            @Override
            public void onResponse(Call<Yard> call, Response<Yard> response) {
                if (response.isSuccessful()) {
                    Yard yardData = response.body();
                    if (yardData != null) {
                        tvNameInYardDetail.setText(yardData.getName());
                        tvPriceInYardDetail.setText("Giá: "+String.valueOf(yardData.getPrice()));
                        tvDescriptionInYardDetail.setText("Mô tả: "+yardData.getDescription());
                        String status = "Đang hoạt động";
                        if(yardData.status==false) status = "Không còn hoạt động";
                        tvStatusInYardDetail.setText(status);
                        yard = yardData;
                        Glide.with(YardDetailActivity.this)
                                .load(yard.getImg())
                                .into(ivYardImage);
                    }
                }

            }

            @Override
            public void onFailure(Call<Yard> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }
}
