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
import com.google.firebase.auth.FirebaseAuth;
import com.softwind.myapplication.R;
import com.softwind.myapplication.databinding.ActivityArticleBinding;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.models.SavedArticles;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ActivityArticleBinding binding;
    private List<SavedArticles> save;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        article = getIntent().getParcelableExtra(MainActivity.EXTRA_ARTICLE);
        if (MainActivity.user.getSavedArticles() != null) {
            save = new ArrayList<>(MainActivity.user.getSavedArticles());
        } else {
            save = new ArrayList<>();
        }
        setContent(article);

        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.bookmarkButton.setOnClickListener(v -> {
            if (!isArticleSaved()) {
                save.add(new SavedArticles(article.getTitle(), article.getLink(), article.getContent(), article.getPubDate(), article.getImage_url()));
                MainActivity.sDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("savedArticles").setValue(save);
                binding.bookmarkButton.setImageResource(R.drawable.round_bookmark_24);
                binding.lavBookmark.playAnimation();
            } else {
                for (SavedArticles savedArticle : save) {
                    if (savedArticle.getTitle().equals(article.getTitle())) {
                        save.remove(savedArticle);
                        break;
                    }
                }
                MainActivity.sDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("savedArticles").setValue(save);
                binding.bookmarkButton.setImageResource(R.drawable.round_bookmark_border_24);
            }
        });

    }

    private boolean isArticleSaved() {
        for (SavedArticles savedArticle : save) {
            if (savedArticle.getTitle().equals(article.getTitle())) {
                return true;
            }
        }
        return false;
    }

    private void setContent(Article article) {
        binding.articleTitle.setText(article.getTitle());
        binding.articleContent.setText((article.getContent() == null) ? "Have a better view on this article by going to the source." : article.getContent());
        binding.articleTime.setText(article.getDateDiff());
        if (isArticleSaved()) {
            binding.bookmarkButton.setImageResource(R.drawable.round_bookmark_24);
        }
        binding.toSourceButton.setOnClickListener(v -> {
            Intent source = new Intent(Intent.ACTION_VIEW);
            source.setData(Uri.parse(article.getLink()));
            startActivity(source);
        });
        if (article.getImage_url() != null) {
            Glide.with(getApplicationContext()).load(article.getImage_url()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
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