package com.softwind.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.softwind.myapplication.R;
import com.softwind.myapplication.activity.BookmarkActivity;
import com.softwind.myapplication.activity.LoginActivity;
import com.softwind.myapplication.activity.MainActivity;
import com.softwind.myapplication.activity.PreferencesActivity;
import com.softwind.myapplication.databinding.FragmentProfileBinding;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public ProfileFragment() {/* Constructor */}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfileBinding.bind(view);

        MainActivity activity = (MainActivity) getActivity();
        FirebaseAuth mAuth = activity.getUser();

        checkUser(mAuth);

        binding.btnLogOut.setOnClickListener(v -> {
            mAuth.signOut();
            checkUser(mAuth);
        });

        binding.btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        binding.btnPreferences.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PreferencesActivity.class);
            System.out.println(mAuth.getUid());
            startActivity(intent);
        });

        binding.btnBookmarks.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BookmarkActivity.class);
            startActivity(intent);
        });

        binding.btnUserSettings.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Hello user!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void checkUser(FirebaseAuth user) {
        if (user.getCurrentUser() == null) {
            binding.profileNewsContainer.setVisibility(View.GONE);
            binding.userContainer.setVisibility(View.GONE);
            binding.guestContainer.setVisibility(View.VISIBLE);
            binding.profileName.setText("Mr. Guest");
            binding.profileEmail.setText("Reader");

        } else {
            binding.profileNewsContainer.setVisibility(View.VISIBLE);
            binding.userContainer.setVisibility(View.VISIBLE);
            binding.guestContainer.setVisibility(View.GONE);
            binding.profileName.setText(user.getCurrentUser().getDisplayName());
            binding.profileEmail.setText(user.getCurrentUser().getEmail());
        }
    }
}