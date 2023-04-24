package com.softwind.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.softwind.myapplication.R;
import com.softwind.myapplication.activity.ArticleActivity;
import com.softwind.myapplication.activity.MainActivity;
import com.softwind.myapplication.adapter.CategoryNewsAdapter;
import com.softwind.myapplication.databinding.FragmentDiscoverBinding;
import com.softwind.myapplication.models.Category;
import com.softwind.myapplication.models.SavedArticles;

public class DiscoverFragment extends Fragment {
    private FragmentDiscoverBinding binding;

    public DiscoverFragment() {/* Constructor */}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDiscoverBinding.bind(view);
        setRecyclerView("world");
        MainActivity.mCategoryCount.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (!MainActivity.categoryMap.get("world").getArticles().isEmpty()) {
                    setRecyclerView("world");
                }
            }
        });
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String categoryName = tab.getText().toString().toLowerCase();
                setRecyclerView(categoryName);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.refreshButton.setOnClickListener(v -> {
            binding.refreshAnimation.setVisibility(View.VISIBLE);
            binding.refreshButton.setVisibility(View.GONE);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                binding.refreshButton.setVisibility(View.VISIBLE);
                binding.refreshAnimation.setVisibility(View.GONE);
            }, 2000);
        });
    }

    private void setRecyclerView(String categoryName) {
        Category category = MainActivity.categoryMap.get(categoryName);
        binding.rvDiscoverNews.setLayoutManager(new LinearLayoutManager(getContext()));
        CategoryNewsAdapter adapter = new CategoryNewsAdapter(category.getArticles());
        adapter.setClickListener(this::goToArticle);
        binding.rvDiscoverNews.setAdapter(adapter);
    }

    private void goToArticle(SavedArticles article) {
        Intent intent = new Intent(getContext(), ArticleActivity.class);
        intent.putExtra(MainActivity.EXTRA_ARTICLE, article);
        startActivity(intent);
    }
}