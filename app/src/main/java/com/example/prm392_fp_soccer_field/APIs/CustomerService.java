package com.example.prm392_fp_soccer_field.APIs;

import com.example.prm392_fp_soccer_field.Models.Customer;
import com.example.prm392_fp_soccer_field.Models.SignUpDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CustomerService {
    @GET("api/Customers")
    Call<List<Customer>> getAllCustomers();
    @POST("api/Customers")
    Call<Customer> SignUp(@Body SignUpDTO dto);
}
