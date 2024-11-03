package com.example.prm392_fp_soccer_field;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prm392_fp_soccer_field.APIs.CustomerService;
import com.example.prm392_fp_soccer_field.APIs.OrderService;
import com.example.prm392_fp_soccer_field.APIs.RetrofitClient;
import com.example.prm392_fp_soccer_field.CallBack.CustomerCallback;
import com.example.prm392_fp_soccer_field.Models.Customer;
import com.example.prm392_fp_soccer_field.Models.SignUpDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtConfirmPassword;
    private Button btnSignUp;
    private TextView tvSignIn;
    private ImageView ivEyePassword, ivEyeConfirm;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Khởi tạo các view
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword2);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignIn); // Thay đổi nếu cần
        tvSignIn = findViewById(R.id.tvSignUp);
        ivEyePassword = findViewById(R.id.ivEye1);
        ivEyeConfirm = findViewById(R.id.ivEye2);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();


        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Xử lý sự kiện nhấp vào nút "Sign Up"
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                // Kiểm tra thông tin đầu vào
                if (email.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password, new CustomerCallback() {
                        @Override
                        public void onCustomerFound(Customer customer) {
                        }
                        @Override
                        public void onError(Throwable throwable) {
                        }
                    });
                }
            }
        });

        ivEyePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(edtPassword, ivEyePassword);
            }
        });

        ivEyeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(edtConfirmPassword, ivEyeConfirm);
            }
        });
    }

    private void registerUser(String email, String password, CustomerCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        SignUpDTO dto = new SignUpDTO(email,password);
                        CustomerService apiService = RetrofitClient.getClient().create(CustomerService.class);
                        Call<Customer> call = apiService.SignUp(dto);
                        call.enqueue(new Callback<Customer>() {
                            @Override
                            public void onResponse(Call<Customer> call, Response<Customer> response) {
                                Customer result = response.body();
                                if(result!=null){
                                    Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                Toast.makeText(SignupActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<Customer> call, Throwable throwable) {
                            }
                        });
                    } else {
                        Toast.makeText(SignupActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void togglePasswordVisibility(EditText editText, ImageView imageView) {
        if (editText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageResource(R.drawable.baseline_remove_red_eye_24);
        } else {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageResource(R.drawable.baseline_visibility_off_24);
        }

        editText.setSelection(editText.getText().length());
    }
}
