package com.softwind.myapplication.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.softwind.myapplication.R;
import com.softwind.myapplication.databinding.ActivityHomeBinding;
import com.softwind.myapplication.fragment.DiscoverFragment;
import com.softwind.myapplication.fragment.HomeFragment;
import com.softwind.myapplication.fragment.ProfileFragment;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.util.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Fragment fragment = new HomeFragment();
    private final List<Article> listArticles = new ArrayList<>();
    public static ObservableInt mBreakObs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setObservable();
        ApiClient.getLatestNews(articles -> Collections.addAll(listArticles, articles));

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

    /**
     * This method create observable for api call. After Async api call done, update
     * the view
     */
    private void setObservable() {
        mBreakObs = new ObservableInt();
        mBreakObs.set(0);
        mBreakObs.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                replaceFragment(fragment);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    public List<Article> getListArticles() {
        return listArticles;
    }
}
