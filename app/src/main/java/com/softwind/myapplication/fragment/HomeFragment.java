package com.softwind.myapplication.fragment;

import static com.softwind.myapplication.activity.MainActivity.categoryMap;
import static com.softwind.myapplication.activity.MainActivity.mCategoryCount;
import static com.softwind.myapplication.activity.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softwind.myapplication.activity.ArticleActivity;
import com.softwind.myapplication.activity.MainActivity;
import com.softwind.myapplication.adapter.CategoryNewsAdapter;
import com.softwind.myapplication.adapter.HomeFragmentAdapter;
import com.softwind.myapplication.databinding.FragmentHomeBinding;
import com.softwind.myapplication.models.Category;
import com.softwind.myapplication.models.SavedArticles;
import com.softwind.myapplication.util.Content;

import java.util.ArrayList;
import java.util.Collections;
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
        mCategoryCount.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                setContent(view);
            }
        });
    }

    private void setContent(@NonNull View view) {
        Category breaking = categoryMap.get("breaking");
        assert breaking != null;
        if (breaking.getIsDone().get() && breaking.getArticles().size() > 0) {
            List<SavedArticles> articles = breaking.getArticles();
            setHeadline(articles.get(0));

            setRvBreakingNews(view, home.rvBreakingNews, articles);
        }

        if (user != null && isFetchPreferenceDone() && mCategoryCount.get() >= user.getPreferences().size()) {
            List<SavedArticles> forYouArticles = new ArrayList<>();
            for (String preference : user.getPreferences()) {
                Category category = categoryMap.get(preference);
                assert category != null;
                forYouArticles.addAll(category.getArticles());
            }
            Collections.shuffle(forYouArticles);

            setRvForYou(view, home.rvForYou, forYouArticles);
        }
    }

    private void setRvForYou(View view, RecyclerView rvForYou, List<SavedArticles> forYouArticles) {
        rvForYou.setLayoutManager(new LinearLayoutManager(view.getContext()));
        CategoryNewsAdapter adapter = new CategoryNewsAdapter(forYouArticles);
        adapter.setClickListener(this::goToArticle);
        rvForYou.setAdapter(adapter);
    }

    private void setRvBreakingNews(@NonNull View view, RecyclerView rv, List<SavedArticles> articles) {
        rv.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(articles);
        adapter.setClickListener(this::goToArticle);
        rv.setAdapter(adapter);
    }

    private boolean isFetchPreferenceDone() {
        for (String preferences : user.getPreferences()) {
            Category category = categoryMap.get(preferences);
            assert category != null;
            if (!category.getIsDone().get()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding home = FragmentHomeBinding.inflate(inflater, container, false);
        return home.getRoot();
    }

    private void setHeadline(SavedArticles article) {
        home.tvTopHeadline.setText(article.getTitle());
        home.rlHeadline.setOnClickListener(v -> goToArticle(article));

        if (getActivity() == null) {
            return;
        }

        if (article.getImage_url() != null) {
            Content.placeImage(getContext(), article, home.topHeadlineImage, home.topHeadlineImageLoading);

        } else {
            Content.setFailedResource(article);
            Content.placeImage(getContext(), article, home.topHeadlineImage, home.topHeadlineImageLoading);

        }

    }

    private void goToArticle(SavedArticles article) {
        Intent intent = new Intent(getContext(), ArticleActivity.class);
        intent.putExtra(MainActivity.EXTRA_ARTICLE, article);
        startActivity(intent);
    }

}