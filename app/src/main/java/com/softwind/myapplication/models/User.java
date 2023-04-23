package com.softwind.myapplication.models;

import java.util.List;

public class User {
    private List<String> preferences;
    private List<String> savedArticles;

    public User() {
    }

    public User(List<String> preferences, List<String> savedArticles) {
        this.preferences = preferences;
        this.savedArticles = savedArticles;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public List<String> getSavedArticles() {
        return savedArticles;
    }

    public void setSavedArticles(List<String> savedArticles) {
        this.savedArticles = savedArticles;
    }
}
