package com.softwind.myapplication.util;

import com.softwind.myapplication.models.Article;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String API_KEY = "pub_2004607fb75c1a216b39fa2d76c5ad8fda9b3";

    public static ApiInterface getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsdata.io/api/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static void getLatestNews(NewsCallback callback) {
        ApiInterface apiService = getService();
        Call<NewsResponse> call = apiService.getLatestNews("us", API_KEY);

        fetchArticles(callback, call);

    }

    public static void getNewsWithCategory(String category, NewsCallback callback) {
        ApiInterface apiService = getService();
        Call<NewsResponse> call = apiService.getNewsWithCategory("us", API_KEY, category);

        fetchArticles(callback, call);
    }
    private static void fetchArticles(NewsCallback callback, Call<NewsResponse> call) {
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = response.body();
                    assert newsResponse != null;
                    Article[] articles = newsResponse.getArticles();
                    callback.onSuccess(articles);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
            }
        });
    }

    public interface NewsCallback {
        void onSuccess(Article[] articles);
    }
}
