package com.example.prm392_fp_soccer_field.Models;

public class SignUpDTO {
    private String email;
    private String password;
    public SignUpDTO(String email,String password){
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
