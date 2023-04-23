package com.softwind.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.softwind.myapplication.databinding.ActivityPreferencesBinding;
import com.softwind.myapplication.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PreferencesActivity extends AppCompatActivity {
    Set<String> newPreferencesList = new HashSet<>();
    private ActivityPreferencesBinding binding;
    SwitchCompat[] switches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreferencesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(v -> finish());
        switches = getSwitches();
        setToggleChangeListener();

        boolean fromRegister = getIntent().getBooleanExtra("TO_PREFERRED_CATEGORIES", false);
        if (fromRegister) {
            binding.btnUpdatePreference.setText("Next");
            binding.backButton.setEnabled(false);
            binding.backButton.setVisibility(View.INVISIBLE);
        } else {
            setPreferences();
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        binding.btnUpdatePreference.setOnClickListener(v -> {
            List<String> preferenceList = new ArrayList<>(newPreferencesList);
            if (fromRegister) { // create new user preferences
                MainActivity.sDatabase.child("users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).setValue(new User(preferenceList, Collections.emptyList()))
                        .addOnSuccessListener(unused -> toProfile());
            } else { // update preferences
                MainActivity.sDatabase.child("users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .child("preferences").setValue(preferenceList)
                        .addOnSuccessListener(unused -> finish());
            }
        });
    }

    private void setPreferences() {
        for (SwitchCompat switchCompat:switches) {
            String switchText = switchCompat.getText().toString().toLowerCase();
            if (MainActivity.user.getPreferences().contains(switchText)) {
                switchCompat.setChecked(true);
                newPreferencesList.add(switchText);
            }
        }
    }

    private SwitchCompat[] getSwitches() {
        return new SwitchCompat[]{
                binding.swBusiness,
                binding.swEntertainment,
                binding.swEnvironment,
                binding.swFood,
                binding.swHealth,
                binding.swPolitics,
                binding.swScience,
                binding.swSports,
                binding.swTechnology,
                binding.swTourism,
                binding.swWorld
        };
    }

    private void toProfile() {
        Intent toHome = new Intent(PreferencesActivity.this, MainActivity.class);
        toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        toHome.putExtra("TO_PROFILE", true);
        startActivity(toHome);
        finish();
    }

    private void setToggleChangeListener() {
        for (SwitchCompat switchCompat : switches) {
           switchCompat.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, switchCompat.getText().toString().toLowerCase()));
        }
    }

    private void updateList(boolean b, String category) {
        if (b) {
            newPreferencesList.add(category);
        } else {
            newPreferencesList.remove(category);
        }
    }
}