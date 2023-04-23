package com.softwind.myapplication.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.softwind.myapplication.databinding.ActivityArticleBinding;
import com.softwind.myapplication.models.Article;

public class ArticleActivity extends AppCompatActivity {
    private ActivityArticleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Article article = getIntent().getParcelableExtra(MainActivity.EXTRA_ARTICLE);
        setContent(article);
        binding.backButton.setOnClickListener(v -> finish());

    }

    private void setContent(Article article) {
        binding.articleTitle.setText(article.getTitle());
        binding.articleContent.setText(article.getContent());
        binding.articleTime.setText(article.getDateDiff());
        binding.toSourceButton.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(article.getLink()));
            startActivity(i);
        });
        if (article.getImage_url() != null) {
//                Glide.with(itemView.getContext()).load(article.getImage_url()).into(binding.breakingNewsImage);
            Glide.with(getApplicationContext()).load(article.getImage_url()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        Toast.makeText(itemView.getContext(), "Oops. There's a problem with your network!", Toast.LENGTH_SHORT).show();
                    binding.lavLoading.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    binding.lavLoading.setVisibility(View.GONE);
                    return false;
                }

            }).into(binding.articleImage);

        }
    }
}