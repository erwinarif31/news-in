package com.softwind.myapplication.fragment;

import static com.softwind.myapplication.activity.MainActivity.mBreakObs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.softwind.myapplication.activity.MainActivity;
import com.softwind.myapplication.adapter.BreakingNewsAdapter;
import com.softwind.myapplication.databinding.FragmentHomeBinding;
import com.softwind.myapplication.models.Article;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding home;

    public HomeFragment() {/* Constructor */}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        home = FragmentHomeBinding.bind(view);
        setContent(home.getRoot());
        mBreakObs.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                setContent(view);
            }
        });
    }

    private void setContent(@NonNull View view) {
        if (mBreakObs.get() == 1) {
            MainActivity activity = (MainActivity) getActivity();
            assert activity != null;
            List<Article> articles = activity.getListArticles();
            setHeadline(articles.get(0));

            home.rvBreakingNews.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            BreakingNewsAdapter breakingNewsAdapter = new BreakingNewsAdapter(articles);
            home.rvBreakingNews.setAdapter(breakingNewsAdapter);
        }
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

        if (article.getImage_url() != null) {
            Glide.with(this).load(article.getImage_url()).into(home.topHeadlineImage);

        } else {
//            String category = article.getCategory()[0];
//            switch (category) {
//                case "top":
//                    break;
//                case "business": -
//                    break;
//                case "science": -
//                    break;
//                case "technology": -
//                    break;
//                case "sports": -
//                    break;
//                case "health": -
//                    break;
//                case "entertainment":
//                    break;
//            }
        }

    }
}