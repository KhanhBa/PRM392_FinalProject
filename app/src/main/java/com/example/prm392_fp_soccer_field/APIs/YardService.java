package com.example.prm392_fp_soccer_field.APIs;

import com.example.prm392_fp_soccer_field.Models.Yard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface YardService {
    @GET("api/Yards")
    Call<List<Yard>> getAllYards();
    @GET("api/Yards/{id}")
    Call<Yard> getYardById(@Path("id") int id);
}
