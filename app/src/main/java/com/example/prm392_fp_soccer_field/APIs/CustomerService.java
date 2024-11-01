package com.example.prm392_fp_soccer_field.APIs;

import com.example.prm392_fp_soccer_field.Models.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CustomerService {
    @GET("api/Customers")
    Call<List<Customer>> getAllCustomers();
}
