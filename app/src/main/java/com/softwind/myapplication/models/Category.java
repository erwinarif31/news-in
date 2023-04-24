package com.softwind.myapplication.models;

import androidx.databinding.ObservableBoolean;

import java.util.List;

public class Category {
    private List<SavedArticles> articles;
    private final ObservableBoolean isDone = new ObservableBoolean(false);

    public long getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(long lastFetched) {
        this.lastFetched = lastFetched;
    }

    private long lastFetched;

    public Category() {
    }

    public Category(List<SavedArticles> articles, long lastFetched) {
        this.articles = articles;
        this.lastFetched = lastFetched;
    }

    public List<SavedArticles> getArticles() {
        return articles;
    }

    public void setArticles(List<SavedArticles> articles) {
        this.articles = articles;
    }

    public ObservableBoolean getIsDone() {
        return isDone;
    }
}
