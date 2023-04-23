package com.softwind.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.softwind.myapplication.databinding.ActivityPreferencesBinding;
import com.softwind.myapplication.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

        boolean fromLogin = getIntent().getBooleanExtra("TO_PREFERRED_CATEGORIES", false);
        binding.backButton.setOnClickListener(v -> finish());
        switches = getSwitches();
        setToggleChangeListener();
        if (fromLogin) {
            binding.btnUpdatePreference.setText("Next");
            binding.backButton.setEnabled(false);
        } else {
            setPreferences();
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        binding.btnUpdatePreference.setOnClickListener(v -> {
            List<String> preferenceList = new ArrayList<>(newPreferencesList);
            if (fromLogin) {
                MainActivity.sDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(new User(preferenceList, new ArrayList<>()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                toProfile();
                            }
                        });
            } else {
                MainActivity.sDatabase.child("users").child(mAuth.getCurrentUser().getUid())
                        .child("preferences").setValue(preferenceList)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                });
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
//        binding.swBusiness.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "business"));
//        binding.swEntertainment.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "entertainment"));
//        binding.swEnvironment.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "environment"));
//        binding.swFood.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "food"));
//        binding.swHealth.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "health"));
//        binding.swPolitics.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "politics"));
//        binding.swScience.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "science"));
//        binding.swSports.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "sports"));
//        binding.swTechnology.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "technology"));
//        binding.swTourism.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "tourism"));
//        binding.swWorld.setOnCheckedChangeListener((compoundButton, b) -> updateList(b, "world"));
    }

    private void updateList(boolean b, String category) {
        if (b) {
            newPreferencesList.add(category);
        } else {
            newPreferencesList.remove(category);
        }
    }
}