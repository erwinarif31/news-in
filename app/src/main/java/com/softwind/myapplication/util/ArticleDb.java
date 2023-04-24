package com.softwind.myapplication.util;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.softwind.myapplication.activity.MainActivity;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.models.Category;
import com.softwind.myapplication.models.SavedArticles;

import java.util.ArrayList;
import java.util.List;

public class ArticleDb {

    public static void fetchAndStoreArticles(Category category, String categoryName) {
        if (categoryName.equals("breaking")) {
            MainActivity.sDatabase.child("breaking").removeValue();
            ApiClient.getLatestNews(category, articles -> {
                for (Article article : articles) {
                    SavedArticles savedArticle = new SavedArticles(article.getTitle(), article.getLink(), article.getContent(), article.getPubDate(), article.getImage_url());
                    save("breaking", savedArticle);
                }
                MainActivity.sDatabase.child("breaking").child("lastFetched").setValue(System.currentTimeMillis());
            });
        } else {
            ApiClient.getNewsWithCategory(categoryName, category, articles -> {
                for (Article article : articles) {
                    SavedArticles savedArticle = new SavedArticles(article.getTitle(), article.getLink(), article.getContent(), article.getPubDate(), article.getImage_url());
                    save(categoryName, savedArticle);
                }
                MainActivity.sDatabase.child(categoryName).child("lastFetched").setValue(System.currentTimeMillis());
            });
        }
    }

    private static void save(String category, SavedArticles savedArticle) {
        MainActivity.sDatabase.child(category).child("articles").push().setValue(savedArticle).addOnSuccessListener(aVoid -> System.out.println("Saved article: " + savedArticle.getTitle()));
    }

    public static void getArticles(String categoryName, OnArticlesFetched callback) {
        MainActivity.sDatabase.child(categoryName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SavedArticles> savedArticlesList = new ArrayList<>();
                long lastFetched = 0;
                if (snapshot.child("lastFetched").getValue() != null) {
                    lastFetched = snapshot.child("lastFetched").getValue(Long.class);
                }
                for (DataSnapshot child : snapshot.child("articles").getChildren()) {
                    SavedArticles savedArticle = child.getValue(SavedArticles.class);
                    savedArticlesList.add(savedArticle);
                }
                callback.onArticlesFetched(savedArticlesList, lastFetched);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface OnArticlesFetched {
        void onArticlesFetched(List<SavedArticles> articles, long lastFetched);
    }
}
