package com.example.prm392_fp_soccer_field.APIs;

import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.Models.Yard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceService {
    @GET("api/Services")
    Call<List<Service>> getAllServices();
}
