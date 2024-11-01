package com.example.prm392_fp_soccer_field;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_fp_soccer_field.APIs.CustomerService;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.CallBack.CustomerCallback;
import com.example.prm392_fp_soccer_field.Models.Customer;
import com.example.prm392_fp_soccer_field.Session.UserSessionManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private ImageView ivEye1;
    private Button btnSignIn;
    private TextView tvSignUp;
    private FirebaseAuth mAuth;
    private boolean isPasswordVisible = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Ánh xạ các thành phần giao diện từ XML
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        ivEye1 = findViewById(R.id.ivEye1);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);

        mAuth = FirebaseAuth.getInstance();

        ivEye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    edtPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivEye1.setImageResource(R.drawable.baseline_remove_red_eye_24); // thay đổi icon
                    isPasswordVisible = false;
                } else {
                    edtPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                    ivEye1.setImageResource(R.drawable.baseline_visibility_off_24); // thay đổi icon
                    isPasswordVisible = true;
                }
                edtPassword.setSelection(edtPassword.getText().length());
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    edtUsername.setError("Username is required!");
                    return;
                }

                if (password.isEmpty()) {
                    edtPassword.setError("Password is required!");
                    return;
                }

                signIn(username, password);
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Toast.makeText(SigninActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();

                        findUser(email, new CustomerCallback() {
                            @Override
                            public void onCustomerFound(Customer customer) {
                                if (customer != null) {
                                    UserSessionManager sessionManager = new UserSessionManager(SigninActivity.this);
                                    sessionManager.saveLoginSession(customer);
                                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SigninActivity.this, "Customer not found.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Toast.makeText(SigninActivity.this, "Error retrieving customer data: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {

                        Toast.makeText(SigninActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void findUser(String email, CustomerCallback callback) {
        CustomerService apiService = RetrofitClient.getClient().create(CustomerService.class);

        Call<List<Customer>> call = apiService.getAllCustomers();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    List<Customer> customers = response.body();
                    if (customers != null) {
                        Customer customer = customers.stream()
                                .filter(x -> x.getEmail().equals(email))
                                .findFirst()
                                .orElse(null);
                        callback.onCustomerFound(customer);
                    } else {
                        callback.onCustomerFound(null);
                    }
                } else {
                    callback.onError(new Exception("Response unsuccessful"));
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable throwable) {
                Log.e("SigninActivity", "API call failed: " + throwable.getMessage());
                callback.onError(throwable);
            }
        });
    }

}
