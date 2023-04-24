package com.softwind.myapplication.util;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
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
                    SavedArticles savedArticle = new SavedArticles(article.getTitle(), article.getLink(), article.getContent(), article.getPubDate(), article.getImage_url(), article.getCategory()[0]);
                    save("breaking", savedArticle);
                }
                MainActivity.sDatabase.child("breaking").child("lastFetched").setValue(System.currentTimeMillis());
            });
        } else {
            ApiClient.getNewsWithCategory(categoryName, category, articles -> {
                for (Article article : articles) {
                    SavedArticles savedArticle = new SavedArticles(article.getTitle(), article.getLink(), article.getContent(), article.getPubDate(), article.getImage_url(), article.getCategory()[0]);
                    save(categoryName, savedArticle);
                }
                MainActivity.sDatabase.child(categoryName).child("lastFetched").setValue(System.currentTimeMillis());
            });
        }
    }

    private static void save(String category, SavedArticles savedArticle) {
        if (category.equals("breaking")) {
            MainActivity.sDatabase.child(category).child("articles").push().setValue(savedArticle);
        } else {
            MainActivity.sDatabase.child(category).child("articles").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    boolean isDuplicate = false;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        SavedArticles savedArticles = child.getValue(SavedArticles.class);
                        if (savedArticles != null && savedArticles.getTitle().equals(savedArticle.getTitle())) {
                            isDuplicate = true;
                            break;
                        }
                    }
                    if (!isDuplicate) {
                        MainActivity.sDatabase.child(category).child("articles").push().setValue(savedArticle);
                    }
                }
            });
        }
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
                    savedArticle.setSource_id(child.getKey());
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
