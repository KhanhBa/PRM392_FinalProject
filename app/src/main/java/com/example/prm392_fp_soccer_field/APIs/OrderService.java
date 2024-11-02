package com.example.prm392_fp_soccer_field.APIs;

import com.example.prm392_fp_soccer_field.Models.CreateOrder;
import com.example.prm392_fp_soccer_field.Models.Order;
import com.example.prm392_fp_soccer_field.Models.Yard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    @GET("api/Orders")
    Call<List<Order>> getAllOrders();
    @POST("api/Orders")
    Call<Order> createOrder(@Body CreateOrder createOrder);
    @GET("api/Orders/{id}")
    Call<Order> getOrderById(@Path("id") int id);

}
