package com.example.prm392_fp_soccer_field.CallBack;

import android.view.View;

import com.example.prm392_fp_soccer_field.Models.AvailableSlot;
import com.example.prm392_fp_soccer_field.Models.OrderDetail;
import com.example.prm392_fp_soccer_field.Models.Service;
import com.example.prm392_fp_soccer_field.Models.Yard;

public interface OnBookServiceClick {
    void onBookServiceClick(OrderDetail orderDetail);
    void offBookServiceClick(OrderDetail orderDetail);
}
