package com.softwind.myapplication.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.softwind.myapplication.R;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.util.ApiClient;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        ApiClient.getLatestNews(articles -> System.out.println(articles.length));
    }
}
