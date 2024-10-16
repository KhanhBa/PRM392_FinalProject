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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText edtEmail, edtUsername, edtPassword, edtConfirmPassword;
    private Button btnSignUp;
    private TextView tvSignIn;
    private ImageView ivEyePassword, ivEyeConfirm;
    private FirebaseAuth mAuth; // Firebase Authentication instance

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Đảm bảo tên tệp XML khớp

        // Khởi tạo các view
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername2);
        edtPassword = findViewById(R.id.edtPassword2);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignIn); // Thay đổi nếu cần
        tvSignIn = findViewById(R.id.tvSignUp);
        ivEyePassword = findViewById(R.id.ivEye1);
        ivEyeConfirm = findViewById(R.id.ivEye2);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Xử lý sự kiện nhấp vào "Sign In" để chuyển về SigninActivity
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
                finish(); // Kết thúc Activity hiện tại nếu không cần quay lại
            }
        });

        // Xử lý sự kiện nhấp vào nút "Sign Up"
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim(); // Sửa lại để lấy email
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                // Kiểm tra thông tin đầu vào
                if (username.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Thực hiện đăng ký Firebase
                    registerUser(email, password);
                }
            }
        });

        // Thay đổi trạng thái hiển thị mật khẩu
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

    // Hàm để đăng ký người dùng với Firebase
    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng ký thành công
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        // Điều hướng đến màn hình chính hoặc trang đăng nhập
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class); // Ví dụ: MainActivity là trang chính
                        startActivity(intent);
                        finish(); // Kết thúc trang đăng ký
                    } else {
                        // Đăng ký thất bại
                        Toast.makeText(SignupActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Hàm để chuyển đổi hiển thị mật khẩu
    private void togglePasswordVisibility(EditText editText, ImageView imageView) {
        if (editText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageResource(R.drawable.baseline_remove_red_eye_24);
        } else {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageResource(R.drawable.baseline_visibility_off_24);
        }
        // Đặt con trỏ về cuối văn bản sau khi thay đổi kiểu input
        editText.setSelection(editText.getText().length());
    }
}
