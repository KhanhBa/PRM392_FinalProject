package com.example.prm392_fp_soccer_field;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookedFieldsFragment extends Fragment {

    private RecyclerView bookedFieldsList;
    private TextView bookingSummary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked_fields, container, false);

        bookedFieldsList = view.findViewById(R.id.booked_fields_list);
        bookingSummary = view.findViewById(R.id.booking_summary);

        // Setup the list of booked fields
        setupBookedFieldsList();

        return view;
    }

    private void setupBookedFieldsList() {
        List<BookedField> bookedFields = getBookedFields();
        BookedFieldAdapter adapter = new BookedFieldAdapter(bookedFields);
        bookedFieldsList.setAdapter(adapter);

        bookingSummary.setText("Bạn có " + bookedFields.size() + " lượt đặt sân");
    }

    private List<BookedField> getBookedFields() {
        List<BookedField> fields = new ArrayList<>();
        fields.add(new BookedField("Sân Mỹ Đình", "20/10/2023", "18:00 - 20:00", "Đã xác nhận"));
        fields.add(new BookedField("Sân Hàng Đẫy", "22/10/2023", "19:00 - 21:00", "Chờ xác nhận"));
        fields.add(new BookedField("Sân Quân khu 7", "25/10/2023", "17:00 - 19:00", "Đã xác nhận"));
        return fields;
    }
}