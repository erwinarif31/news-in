package com.softwind.myapplication.models;

import androidx.databinding.ObservableBoolean;

import java.util.List;

public class Category {
    private List<Article> articles;
    private final ObservableBoolean isDone = new ObservableBoolean(false);

    public Category(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public ObservableBoolean getIsDone() {
        return isDone;
    }
}
