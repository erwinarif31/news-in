package com.softwind.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.softwind.myapplication.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private ProgressDialog progressDialog;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        if (mAuth.getCurrentUser() != null) openAuth();
        setContentView(binding.getRoot());

        binding.toLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            String fullName = String.valueOf(binding.inputFullName.getText());
            String email = String.valueOf(binding.inputEmail.getText());
            String password = String.valueOf(binding.inputPassword.getText());
            String confirmPassword = String.valueOf(binding.inputPasswordConfirmation.getText());
            registerAuth(fullName, email, password, confirmPassword);
        });

    }

    private void registerAuth(String fullName, String email, String password, String confirmPassword) {

        if (fullName.isEmpty()) {
            binding.inputFullName.setError("Your Full name is required!");
            return;

        } else if (email.isEmpty()) {
            binding.inputEmail.setError("Email is required!");
            return;

        } else if (password.isEmpty()) {
            binding.inputPassword.setError("Password is required!");
            return;

        } else if (confirmPassword.isEmpty()) {
            binding.inputPasswordConfirmation.setError("Confirm password is required!");
            return;

        } else if (!password.equals(confirmPassword)) {
            binding.inputPassword.setError("Password doesn't match!");
            binding.inputPasswordConfirmation.setError("Password doesn't match!");
            return;
        }

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Registering...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest userData = new UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(fullName)
                                .build();

                        assert user != null;
                        user.updateProfile(userData).addOnCompleteListener(auth -> {
                            if (auth.isSuccessful()) {
//                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                openLogin();
                            }
                        });

                    } else
                        binding.inputEmail.setError("We cannot fetch this one!");

                    progressDialog.dismiss();
                });
    }

    private void openAuth() {
        Intent toHome = new Intent(RegisterActivity.this, MainActivity.class);
        toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        toHome.putExtra("TO_PROFILE", false);
        startActivity(toHome);
        finish();
    }

    private void openLogin() {
        Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        toLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        toLogin.putExtra("FROM_REGISTER", true);
        startActivity(toLogin);
        finish();
    }
}