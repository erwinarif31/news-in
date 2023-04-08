package com.softwind.myapplication.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.softwind.myapplication.R;
import com.softwind.myapplication.databinding.ActivityHomeBinding;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.util.ApiClient;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        ApiClient.getLatestNews(articles -> setHeadline(articles[1]));
        ApiClient.getNewsWithCategory("science", articles -> setHeadline(articles[0]));
    }

    private void setHeadline(Article article) {
        binding.tvTopHeadline.setText(article.getTitle());
        Glide.with(this).load(article.getImage_url()).into(binding.topHeadlineImage);
    }
}
