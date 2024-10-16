package com.example.prm392_fp_soccer_field;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

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

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Xử lý sự kiện khi nhấn vào icon con mắt để ẩn/hiện mật khẩu
        ivEye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Nếu mật khẩu đang hiển thị, thì ẩn nó đi
                    edtPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivEye1.setImageResource(R.drawable.baseline_remove_red_eye_24); // thay đổi icon
                    isPasswordVisible = false;
                } else {
                    // Hiển thị mật khẩu
                    edtPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                    ivEye1.setImageResource(R.drawable.baseline_visibility_off_24); // thay đổi icon
                    isPasswordVisible = true;
                }
                // Đặt con trỏ về cuối văn bản sau khi thay đổi kiểu input
                edtPassword.setSelection(edtPassword.getText().length());
            }
        });

        // Xử lý sự kiện khi nhấn nút "Sign In"
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // Kiểm tra đầu vào
                if (username.isEmpty()) {
                    edtUsername.setError("Username is required!");
                    return;
                }

                if (password.isEmpty()) {
                    edtPassword.setError("Password is required!");
                    return;
                }

                // Gọi hàm đăng nhập với Firebase Authentication
                signIn(username, password);
            }
        });

        // Xử lý sự kiện khi nhấn vào "Sign Up" để chuyển sang SignupActivity
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Hàm đăng nhập với Firebase Authentication
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Toast.makeText(SigninActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        // Ví dụ: startActivity(new Intent(SigninActivity.this, MainActivity.class));
                    } else {

                        Toast.makeText(SigninActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
