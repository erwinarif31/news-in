package com.softwind.myapplication.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.softwind.myapplication.R;
import com.softwind.myapplication.databinding.ActivityHomeBinding;
import com.softwind.myapplication.fragment.DiscoverFragment;
import com.softwind.myapplication.fragment.HomeFragment;
import com.softwind.myapplication.fragment.ProfileFragment;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.models.Category;
import com.softwind.myapplication.util.ApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Fragment fragment = new HomeFragment();
    private final List<Article> breakingArticles = new ArrayList<>();
    public final static ObservableInt mCategoryCount = new ObservableInt(0);
    public static Map<String, Category> categoryMap = new HashMap<>();

    public static String[] userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userPreferences = new String[]{"health", "sports", "science"}; // temporary preferences

        replaceFragment(fragment);
        setCategoryMap(categoryMap);
        setNavbarListener();
        fetchBreakingArticles();
        fetchArticlesPreferences(userPreferences);

    }

    private void fetchBreakingArticles() {
        Category category = categoryMap.get("breaking");
        ApiClient.getLatestNews(category, articles -> {
            assert category != null;
            category.setArticles(Arrays.asList(articles));
        });
    }

    private void fetchArticlesPreferences(String[] userPreferences) {
        for (String preference : userPreferences) {
            Category category = categoryMap.get(preference);

            ApiClient.getNewsWithCategory(preference, category, articles -> category.setArticles(Arrays.asList(articles)));
        }
    }

    private void setNavbarListener() {
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

    public static void setCategoryMap(Map<String, Category> categoryMap) {
        categoryMap.put("breaking", new Category(Collections.emptyList()));
        categoryMap.put("health", new Category(Collections.emptyList()));
        categoryMap.put("sports", new Category(Collections.emptyList()));
        categoryMap.put("business", new Category(Collections.emptyList()));
        categoryMap.put("science", new Category(Collections.emptyList()));
        categoryMap.put("technology", new Category(Collections.emptyList()));
        categoryMap.put("entertainment", new Category(Collections.emptyList()));
    }

    public void setUserPreferences(String[] userPreferences) {
        this.userPreferences = userPreferences;
    }

}
