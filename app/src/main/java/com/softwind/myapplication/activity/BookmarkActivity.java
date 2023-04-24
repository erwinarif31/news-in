package com.softwind.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.softwind.myapplication.adapter.BookmarkAdapter;
import com.softwind.myapplication.databinding.ActivityBookmarkBinding;
import com.softwind.myapplication.models.SavedArticles;

public class BookmarkActivity extends AppCompatActivity {
    private ActivityBookmarkBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookmarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        setBookmark();

        binding.backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void setBookmark() {
        if (MainActivity.user.getSavedArticles() != null && MainActivity.user.getSavedArticles().size() > 0) {
            binding.tvNoBookmark.setVisibility(View.GONE);
            BookmarkAdapter adapter = new BookmarkAdapter(MainActivity.user.getSavedArticles());
            binding.rvBookmark.setAdapter(adapter);
            adapter.setClickListener(this::goToArticle);
            adapter.setRemoveListener(this::removeBookmark);

        } else {
            binding.tvNoBookmark.setVisibility(View.VISIBLE);
        }
    }
    private void goToArticle(SavedArticles article) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(MainActivity.EXTRA_ARTICLE, article);
        startActivity(intent);
    }

    private void removeBookmark(SavedArticles article) {
        MainActivity.user.getSavedArticles().remove(article);
        MainActivity.sDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(MainActivity.user);
        setBookmark();
    }
}