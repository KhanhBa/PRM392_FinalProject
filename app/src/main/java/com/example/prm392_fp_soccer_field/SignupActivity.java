package com.example.prm392_fp_soccer_field;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtConfirmPassword;
    private Button btnSignUp;
    private TextView tvSignIn;
    private ImageView ivEyePassword, ivEyeConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Đảm bảo tên tệp XML khớp

        // Khởi tạo các view
        edtUsername = findViewById(R.id.edtUsername2);
        edtPassword = findViewById(R.id.edtPassword2);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignIn);
        tvSignIn = findViewById(R.id.tvSignUp);
        ivEyePassword = findViewById(R.id.ivEye1);
        ivEyeConfirm = findViewById(R.id.ivEye2);

        // Chuyển đến Sign In Activity khi nhấp vào "Sign Up"
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
                finish(); // Kết thúc Activity hiện tại
            }
        });

        // Xử lý sự kiện nhấp vào nút "Sign Up"
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                // Kiểm tra thông tin đầu vào
                if (username.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý đăng ký (kết nối đến API hoặc lưu trữ cục bộ)
                    // ...

                    Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
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

    // Hàm để chuyển đổi hiển thị mật khẩu
    private void togglePasswordVisibility(EditText editText, ImageView imageView) {
        if (editText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageResource(R.drawable.baseline_remove_red_eye_24); // Chuyển về hình mắt đóng
        } else {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageResource(R.drawable.baseline_remove_red_eye_24); // Chuyển về hình mắt mở
        }
        editText.setSelection(editText.getText().length()); // Đặt con trỏ về cuối văn bản
    }
}