package com.example.prm392_fp_soccer_field;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private ListView itemListView;
    private Button submitButton;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        itemListView = findViewById(R.id.itemListView);
        submitButton = findViewById(R.id.Submit);

        // Khởi tạo và thiết lập adapter cho ListView
        itemAdapter = new ItemAdapter(this, getItemList());
        itemListView.setAdapter(itemAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấn nút xác nhận
                // Thực hiện các thao tác thanh toán ở đây
                handlePayment();
            }
        });
    }

    // Hàm để lấy danh sách item
    private Item[] getItemList() {
        return new Item[]{
                new Item("Nước", 0),
                new Item("Áo", 0),
                new Item("Trọng Tài", 0)
        };
    }

    private void handlePayment() {
        // Xử lý thanh toán ở đây
        // Bạn có thể lấy thông tin từ itemAdapter để thực hiện thanh toán
    }
}
