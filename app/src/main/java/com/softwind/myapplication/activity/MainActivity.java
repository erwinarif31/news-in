package com.softwind.myapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softwind.myapplication.R;
import com.softwind.myapplication.databinding.ActivityHomeBinding;
import com.softwind.myapplication.fragment.DiscoverFragment;
import com.softwind.myapplication.fragment.HomeFragment;
import com.softwind.myapplication.fragment.ProfileFragment;
import com.softwind.myapplication.models.Category;
import com.softwind.myapplication.models.User;
import com.softwind.myapplication.util.ArticleDb;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final ObservableInt mCategoryCount = new ObservableInt(0);
    public static final String EXTRA_ARTICLE = "extra_article";
    public static Map<String, Category> categoryMap = new HashMap<>();
    public static User user;
    public static DatabaseReference sDatabase = FirebaseDatabase.getInstance("https://news-in-932a2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    private ActivityHomeBinding binding;
    private Fragment fragment = new HomeFragment();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        boolean toProfile = getIntent().getBooleanExtra("TO_PROFILE", false);
        if (toProfile) {
            fragment = new ProfileFragment();
            binding.bottomNavbar.setSelectedItemId(R.id.navbar_profile);
            replaceFragment(fragment);
        } else {
            showSplash();
        }

        getUserData(mAuth.getCurrentUser());
        setCategoryMap(categoryMap);
        replaceFragment(fragment);
        setNavbarListener();
        fetchBreakingArticles();
        fetchArticles();

//        ArticleDb.fetchAndStoreArticles(categoryMap.get("breaking"), "breaking");

    }

    private void getUserData(FirebaseUser loggedUser) {
        if (loggedUser == null) return; // Filter for guest
        sDatabase.child("users").child(loggedUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchBreakingArticles() {
        ArticleDb.getArticles("breaking", (articles, lastFetched) -> {
            categoryMap.get("breaking").setArticles(articles);
            categoryMap.get("breaking").setLastFetched(lastFetched);
            categoryMap.get("breaking").getIsDone().set(true);
            mCategoryCount.set(mCategoryCount.get() + 1);
        });
    }

    private void fetchArticles() {
        Set<String> keys = categoryMap.keySet();
        for (String categoryName : keys) {
            if (categoryName.equals("breaking")) continue;
            ArticleDb.getArticles(categoryName, (articles, lastFetched) -> {
                if (articles == null) return;
                categoryMap.get(categoryName).setArticles(articles);
                categoryMap.get(categoryName).setLastFetched(lastFetched);
                categoryMap.get(categoryName).getIsDone().set(true);
                mCategoryCount.set(mCategoryCount.get() + 1);
            });
        }
    }

    public static void setCategoryMap(Map<String, Category> categoryMap) {
        categoryMap.put("breaking", new Category(Collections.emptyList(), 0));
        categoryMap.put("health", new Category(Collections.emptyList(), 0));
        categoryMap.put("sports", new Category(Collections.emptyList(), 0));
        categoryMap.put("business", new Category(Collections.emptyList(), 0));
        categoryMap.put("science", new Category(Collections.emptyList(), 0));
        categoryMap.put("technology", new Category(Collections.emptyList(), 0));
        categoryMap.put("entertainment", new Category(Collections.emptyList(), 0));
        categoryMap.put("environment", new Category(Collections.emptyList(), 0));
        categoryMap.put("food", new Category(Collections.emptyList(), 0));
        categoryMap.put("politics", new Category(Collections.emptyList(), 0));
        categoryMap.put("tourism", new Category(Collections.emptyList(), 0));
        categoryMap.put("world", new Category(Collections.emptyList(), 0));
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

    public void showSplash() {
        Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Handler handler  = new Handler();
        Runnable runnable = dialog::dismiss;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.splash_screen);
        dialog.setCancelable(true);
        dialog.show();

        handler.postDelayed(runnable, 3200);
    }

    public FirebaseAuth getUser() {
        return this.mAuth;
    }
}
