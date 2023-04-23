package com.softwind.myapplication.models;

import java.util.List;

public class User {
    private List<String> preferences;
    private List<SavedArticles> savedArticles;

    public User() {/* Constructor */}

    public User(List<String> preferences, List<SavedArticles> savedArticles) {
        this.preferences = preferences;
        this.savedArticles = savedArticles;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public List<SavedArticles> getSavedArticles() {
        return savedArticles;
    }

    public void setSavedArticles(List<SavedArticles> savedArticles) {
        this.savedArticles = savedArticles;
    }
}
