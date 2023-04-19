package com.softwind.myapplication.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.softwind.myapplication.R;
import com.softwind.myapplication.databinding.ActivityHomeBinding;
import com.softwind.myapplication.fragment.DiscoverFragment;
import com.softwind.myapplication.fragment.HomeFragment;
import com.softwind.myapplication.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Fragment fragment = new HomeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(fragment);

        binding.bottomNavbar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navbar_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navbar_discover:
                    fragment = new DiscoverFragment();
                    break;
                case R.id.navbar_profile:
                    fragment = new ProfileFragment();
                    break;
            }
            replaceFragment(fragment);
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

}
