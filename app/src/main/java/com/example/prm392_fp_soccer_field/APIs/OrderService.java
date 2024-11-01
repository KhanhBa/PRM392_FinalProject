package com.example.prm392_fp_soccer_field.APIs;

import com.example.prm392_fp_soccer_field.Models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderService {
    @GET("api/Orders")
    Call<List<Order>> getAllOrders();
}
