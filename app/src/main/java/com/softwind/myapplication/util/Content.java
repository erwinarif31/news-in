package com.softwind.myapplication.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.models.SavedArticles;

public class Content {
    public static final String WORLD = "https://i.ibb.co/BGtKMX1/world.jpg";
    public static final String TOURISM = "https://i.ibb.co/SRrzJkL/tourism.jpg";
    public static final String TECHNOLOGY = "https://i.ibb.co/nmFDXqk/technology.jpg";
    public static final String SPORTS = "https://i.ibb.co/sCRhVhk/sports.jpg";
    public static final String SCIENCE = "https://i.ibb.co/HCfjK0X/science.jpg";
    public static final String POLITICS = "https://i.ibb.co/qxNKp2y/politics.jpg";
    public static final String HEALTH = "https://i.ibb.co/VmNwGzN/health.jpg";
    public static final String FOOD = "https://i.ibb.co/ZVxd0xB/food.jpg";
    public static final String ENVIRONMENT = "https://i.ibb.co/k6BBBLb/environment.jpg";
    public static final String ENTERTAINMENT = "https://i.ibb.co/98gmSCW/entertainment.jpg";
    public static final String BUSINESS = "https://i.ibb.co/thMzH1n/business.jpg";

    public static void setFailedResource(SavedArticles savedArticles) {
        Article article = new Article(savedArticles);
        switch (article.getCategory()[0].toLowerCase()) {
            case "business":
                article.setImage_url(Content.BUSINESS);
                break;
            case "sports":
                article.setImage_url(Content.SPORTS);
                break;
            case "entertainment":
                article.setImage_url(Content.ENTERTAINMENT);
                break;
            case "health":
                article.setImage_url(Content.HEALTH);
                break;
            case "science":
                article.setImage_url(Content.SCIENCE);
                break;
            case "technology":
                article.setImage_url(Content.TECHNOLOGY);
                break;
            case "world":
                article.setImage_url(Content.WORLD);
                break;
            case "politics":
                article.setImage_url(Content.POLITICS);
                break;
            case "tourism":
                article.setImage_url(Content.TOURISM);
                break;
            case "food":
                article.setImage_url(Content.FOOD);
                break;
            case "environment":
                article.setImage_url(Content.ENVIRONMENT);
                break;
        }
    }

    public static void placeImage(Context context, SavedArticles article, ImageView placeholder, ImageView lavLoading) {
        Glide.with(context).load(article.getImage_url()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                lavLoading.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                lavLoading.setVisibility(View.GONE);
                return false;
            }

        }).into(placeholder);
    }
}
