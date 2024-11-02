package com.example.prm392_fp_soccer_field.APIs;

import com.example.prm392_fp_soccer_field.Models.OrderDetail;
import com.example.prm392_fp_soccer_field.Models.Yard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderDetailService {
    @GET("api/OrderDetails/orders/{id}")
    Call<List<OrderDetail>> getOrderDetailsByOrderId(@Path("id") int id);
}
