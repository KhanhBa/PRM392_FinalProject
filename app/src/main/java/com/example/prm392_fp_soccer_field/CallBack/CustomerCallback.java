package com.example.prm392_fp_soccer_field.CallBack;

import com.example.prm392_fp_soccer_field.Models.Customer;

public interface CustomerCallback {
    void onCustomerFound(Customer customer);
    void onError(Throwable throwable);
}
