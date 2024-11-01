package com.example.prm392_fp_soccer_field.APIs;

import com.example.prm392_fp_soccer_field.Models.AvailableSlot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SlotService {
    @GET("api/Yards/{id}/Days/{day}")
    Call<List<AvailableSlot>> getAllAvailableSlots(@Path("id") int id, @Path("day") String day);
}
