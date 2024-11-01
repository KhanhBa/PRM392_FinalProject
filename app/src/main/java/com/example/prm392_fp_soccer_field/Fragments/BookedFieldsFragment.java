package com.example.prm392_fp_soccer_field.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_fp_soccer_field.APIs.OrderService;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.Adapters.BookedFieldAdapter;
import com.example.prm392_fp_soccer_field.Models.BookedField;
import com.example.prm392_fp_soccer_field.Models.Customer;
import com.example.prm392_fp_soccer_field.Models.Order;
import com.example.prm392_fp_soccer_field.R;
import com.example.prm392_fp_soccer_field.Session.UserSessionManager;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedFieldsFragment extends Fragment {

    private RecyclerView bookedFieldsList;
    private TextView bookingSummary;
    private List<Order> orderList;
    private int customerId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked_fields, container, false);

        bookedFieldsList = view.findViewById(R.id.booked_fields_list);
        bookingSummary = view.findViewById(R.id.booking_summary);
        UserSessionManager sessionManager = new UserSessionManager(getContext());

        if (sessionManager.isLoggedIn()) {
            Customer loggedInUser = sessionManager.getUser();
            customerId=loggedInUser.getId();
        }
        setupOrderList();
        return view;
    }

    private void setupBookedFieldsList() {
        List<BookedField> bookedFields = getBookedFields();
        BookedFieldAdapter adapter = new BookedFieldAdapter(bookedFields);
        bookedFieldsList.setAdapter(adapter);

        bookingSummary.setText("Bạn có " + bookedFields.size() + " lượt đặt sân");
    }

    private List<BookedField> getBookedFields() {
        return orderList.stream()
                .map(x -> new BookedField(
                        x.getYard().getName(),
                        x.getBookingOrder(),
                        x.getStartedTime() + " - " + x.getEndedTime(),
                        x.isStatus() ? "Đã xác nhận" : "Đã Hủy"
                ))
                .collect(Collectors.toList());
    }
    private void setupOrderList() {
        OrderService apiService = RetrofitClient.getClient().create(OrderService.class);
        Call<List<Order>> call = apiService.getAllOrders();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    List<Order> orders = response.body();
                    if (orders != null) {
                        orderList = orders.stream().filter(x->x.getCustomerId()==customerId).collect(Collectors.toList());
                        setupBookedFieldsList();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }
}