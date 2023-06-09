package com.softwind.myapplication.util;

import androidx.annotation.NonNull;

import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.models.Category;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String API_KEY = "pub_208719a38deaf501013ca72630db229e3c8ae";

    public static ApiInterface getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsdata.io/api/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static void getLatestNews(Category category, NewsCallback callback) {
        ApiInterface apiService = getService();
        Call<NewsResponse> call = apiService.getLatestNews("us", API_KEY);
        fetchArticles(callback, call, category);
    }

    public static void getNewsWithCategory(String categoryName, Category category, NewsCallback callback) {
        ApiInterface apiService = getService();
        Call<NewsResponse> call = apiService.getNewsWithCategory("us", API_KEY, categoryName);
        fetchArticles(callback, call, category);
    }

    private static void fetchArticles(NewsCallback callback, Call<NewsResponse> call, Category category) {
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = response.body();
                    assert newsResponse != null;
                    Article[] articles = newsResponse.getArticles();
                    callback.onSuccess(articles);
                    category.getIsDone().set(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public interface NewsCallback {
        void onSuccess(Article[] articles);
    }
}
