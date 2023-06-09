package com.softwind.myapplication.activity;

import static com.softwind.myapplication.activity.MainActivity.sDatabase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.softwind.myapplication.R;
import com.softwind.myapplication.databinding.ActivityArticleBinding;
import com.softwind.myapplication.models.SavedArticles;
import com.softwind.myapplication.util.Content;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ArticleActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ActivityArticleBinding binding;
    private List<SavedArticles> save;
    private SavedArticles article;
    private boolean isSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        article = getIntent().getParcelableExtra(MainActivity.EXTRA_ARTICLE);
        if (mAuth.getCurrentUser() != null && MainActivity.user.getSavedArticles() != null) {
            save = new ArrayList<>(MainActivity.user.getSavedArticles());
        } else {
            save = new ArrayList<>();
        }
        setContent(article);

        binding.backButton.setOnClickListener(v -> finish());

        binding.bookmarkButton.setOnClickListener(v -> {
            if (mAuth.getCurrentUser() == null) {
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }

            if (!isArticleSaved()) {
                save.add(article);
                sDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("savedArticles").setValue(save).addOnSuccessListener(aVoid -> {
                    binding.bookmarkButton.setImageResource(R.drawable.round_bookmark_24);
                    binding.lavBookmark.playAnimation();
                });
            } else {
                for (SavedArticles savedArticle : save) {
                    if (savedArticle.getTitle().equals(article.getTitle())) {
                        save.remove(savedArticle);
                        break;
                    }
                }
                sDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("savedArticles").setValue(save).addOnSuccessListener(aVoid -> {
                    binding.bookmarkButton.setImageResource(R.drawable.round_bookmark_border_24);
                });
            }
        });

        binding.moreButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.popup_more, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(x -> {
                switch (x.getItemId()) {
                    case R.id.action_share:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT, article.getLink());
                        startActivity(Intent.createChooser(share, "Share this article"));
                        break;
                    case R.id.action_open_in_browser:
                        Intent browser = new Intent(Intent.ACTION_VIEW);
                        browser.setData(Uri.parse(article.getLink()));
                        startActivity(browser);
                        break;
                }
                return true;
            });
            popupMenu.show();
        });

    }

    private boolean isArticleSaved() {
        for (SavedArticles savedArticle : save) {
            if (savedArticle.getTitle().equals(article.getTitle())) {
                return true;
            }
        };
        return false;
    }

    private void setContent(SavedArticles article) {
        binding.articleTitle.setText(article.getTitle());

        binding.articleTime.setText(article.getDateDiff());
        if (isArticleSaved()) {
            binding.bookmarkButton.setImageResource(R.drawable.round_bookmark_24);
        }

        if (article.getContent() != null) {
            int start = 0, end = 0, chunk = 10;
            List<String> sentences = new ArrayList<>();
            String rawContent = article.getContent();
            StringBuilder content = new StringBuilder();
            BreakIterator bi = BreakIterator.getSentenceInstance(Locale.ENGLISH);
            bi.setText(rawContent);

            while ((end = bi.next()) != BreakIterator.DONE) {
                sentences.add(rawContent.substring(start, end));
                start = end;
            }

            for (int i=0; i<sentences.size(); i+=chunk) {
                content.append(Arrays.toString(Arrays.copyOfRange(sentences.toArray(), i, Math.min(sentences.size(), i + chunk)))
                                .replace("[", "")
                                .replace("]", "")
                                .replace(". ,", ".")
                                .replace("[\"\\s,]", "\"")
                                .replace("['\\s,]", "\"")
                        ).append("\n\n");
            }

            binding.articleContent.setText(content.toString());
            binding.toSourceButton.setVisibility(View.GONE);

        } else {
            binding.articleContent.setText("Have a better experience by opening this article in browser. Click the button below to open this article in browser.");
            binding.toSourceButton.setVisibility(View.VISIBLE);
            binding.toSourceButton.setOnClickListener(v -> {
                Intent source = new Intent(Intent.ACTION_VIEW);
                source.setData(Uri.parse(article.getLink()));
                startActivity(source);
            });
        }

        if (article.getImage_url() != null) {
            Content.placeImage(this, article, binding.articleImage, binding.lavLoading);
        } else {
            Content.setFailedResource(article);
            Content.placeImage(this, article, binding.articleImage, binding.lavLoading);
        }
    }
}