package com.example.prm392_fp_soccer_field.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_fp_soccer_field.APIs.OrderService;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.APIs.YardService;
import com.example.prm392_fp_soccer_field.Adapters.BookedFieldAdapter;
import com.example.prm392_fp_soccer_field.Adapters.YardListAdapter;
import com.example.prm392_fp_soccer_field.CallBack.OnYardClickListener;
import com.example.prm392_fp_soccer_field.CallBack.OrderedClickListener;
import com.example.prm392_fp_soccer_field.Models.BookedField;
import com.example.prm392_fp_soccer_field.Models.Customer;
import com.example.prm392_fp_soccer_field.Models.Order;
import com.example.prm392_fp_soccer_field.Models.Yard;
import com.example.prm392_fp_soccer_field.OrderDetailActivity;
import com.example.prm392_fp_soccer_field.R;
import com.example.prm392_fp_soccer_field.Session.UserSessionManager;
import com.example.prm392_fp_soccer_field.YardDetailActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

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
    private List<Yard> yards;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked_fields, container, false);

        bookedFieldsList = view.findViewById(R.id.booked_fields_list);
        bookingSummary = view.findViewById(R.id.booking_summary);
        UserSessionManager sessionManager = new UserSessionManager(getContext());
        setupYardList();
        if (sessionManager.isLoggedIn()) {
            Customer loggedInUser = sessionManager.getUser();
            customerId=loggedInUser.getId();
        }
        setupOrderList();
        return view;
    }

    private void setupBookedFieldsList() {
        List<BookedField> bookedFields = getBookedFields();
        BookedFieldAdapter adapter = new BookedFieldAdapter(bookedFields, new OrderedClickListener() {
            @Override
            public void onOrderClick(BookedField bookedField) {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("orderId",bookedField.getId());
                startActivity(intent);
            }
        });
        bookedFieldsList.setAdapter(adapter);

        bookingSummary.setText("Bạn có " + bookedFields.size() + " lượt đặt sân");
    }

    private List<BookedField> getBookedFields() {
        return orderList.stream()
                .map(x -> {
                    Yard yard = yards.stream().filter(y->y.id==x.getYardId()).findAny().get();
                    BookedField newBookField = new BookedField(
                        yard.getName(),
                        x.getBookingDate(),
                        x.getStartTime() + " - " + x.getEndTime(),
                        x.getStatus()
                );
                    newBookField.setId(x.getId());
                    return newBookField;
                })
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

    private void setupYardList() {
        YardService apiService = RetrofitClient.getClient().create(YardService.class);


        Call<List<Yard>> call = apiService.getAllYards();
        call.enqueue(new Callback<List<Yard>>() {
            @Override
            public void onResponse(Call<List<Yard>> call, Response<List<Yard>> response) {
                if (response.isSuccessful()) {
                   yards = response.body();

                }
            }

            @Override
            public void onFailure(Call<List<Yard>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed: " + throwable.getMessage());
            }

        });
    }

}