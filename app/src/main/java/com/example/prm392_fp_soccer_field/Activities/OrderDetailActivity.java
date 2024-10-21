package com.example.prm392_fp_soccer_field.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_fp_soccer_field.Models.DatabaseContext;
import com.example.prm392_fp_soccer_field.Models.Order;
import com.example.prm392_fp_soccer_field.R;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class OrderDetailActivity extends AppCompatActivity {
    public TextView tvDateBooking;
    DatabaseContext databaseContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_activity);
        Order order = null;
        try {
            databaseContext= new DatabaseContext();
            Dao<Order, Integer> orderDao = databaseContext.getDao(Order.class);
            order = orderDao.queryForId(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tvDateBooking.setText(order.getCreatedDate().toString());
    }
    public void init(){
        tvDateBooking= findViewById(R.id.tvDateBookingInOrderDetail);
    }
}
