package com.softwind.myapplication.util;

import com.google.gson.annotations.SerializedName;
import com.softwind.myapplication.models.Article;

public class NewsResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("results")
    private Article[] results;
    @SerializedName("nextPage")
    private String nextPage;

    public String getNextPage() {
        return nextPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public Article[] getArticles() {
        return results;
    }
}
