package com.softwind.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.softwind.myapplication.databinding.FragmentHomeBinding;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.util.ApiClient;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding home;

    public HomeFragment() {/* Constructor */}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiClient.getNewsWithCategory("science", articles -> setHeadline(articles[1]));

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        home = FragmentHomeBinding.bind(view);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding home = FragmentHomeBinding.inflate(inflater, container, false);
        return home.getRoot();
    }

    private void setHeadline(Article article) {
//        System.out.println("Title: " + article.getTitle());
//        System.out.println("Image: " + article.getImage_url());
//        System.out.println("Category: " + Arrays.toString(article.getCategory()));

        home.tvTopHeadline.setText(article.getTitle());

        if (!article.getImage_url().isEmpty() || article != null) {
            Glide.with(this).load(article.getImage_url()).into(home.topHeadlineImage);

        } else {
//            String category = article.getCategory()[0];
//            switch (category) {
//                case "top":
//                    break;
//                case "business":
//                    break;
//                case "science":
//                    break;
//                case "technology":
//                    break;
//                case "sports":
//                    break;
//                case "health":
//                    break;
//                case "entertainment":
//                    break;
//            }
        }

    }
}