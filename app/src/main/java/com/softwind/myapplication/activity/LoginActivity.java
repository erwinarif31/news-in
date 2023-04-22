package com.softwind.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.softwind.myapplication.R;
import com.softwind.myapplication.databinding.ActivityLoginBinding;
import com.softwind.myapplication.fragment.ProfileFragment;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        Boolean fromRegister = getIntent().getBooleanExtra("FROM_REGISTER", false);
        if (mAuth.getCurrentUser() != null) openAuth(fromRegister);

        setContentView(binding.getRoot());

        binding.toSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        binding.btnSignIn.setOnClickListener(v -> {
            String email = binding.inputEmail.getText().toString();
            String password = binding.inputPassword.getText().toString();
            loginAuth(email, password);
        });

    }


    private void loginAuth(String email, String password) {

        if (email.isEmpty() || password.isEmpty()) {
            binding.inputEmail.setError("Email is required");
            binding.inputPassword.setError("Password is required");
            return;
        }

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
//                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                openAuth(true);

            } else {
                // If email is not found
                if (task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                    binding.inputEmail.setError("Email does not exist!");
                }
                // If password is incorrect
                if (task.getException().getMessage().equals("The password is invalid or the user does not have a password.")) {
                    binding.inputPassword.setError("Password is incorrect!");
                }
            }

            progressDialog.dismiss();
        });

    }

    private void openAuth(Boolean toProfile) {
        Intent toHome = new Intent(LoginActivity.this, MainActivity.class);
        toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        toHome.putExtra("TO_PROFILE", toProfile);
        startActivity(toHome);
        finish();
    }
}