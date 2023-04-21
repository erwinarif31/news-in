package com.softwind.myapplication.fragment;

import static com.softwind.myapplication.activity.MainActivity.categoryMap;
import static com.softwind.myapplication.activity.MainActivity.mCategoryCount;
import static com.softwind.myapplication.activity.MainActivity.userPreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softwind.myapplication.adapter.HomeFragmentAdapter;
import com.softwind.myapplication.databinding.FragmentHomeBinding;
import com.softwind.myapplication.models.Article;
import com.softwind.myapplication.models.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding home;
    private List<Article> forYouArticles;

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
        if (breaking.getIsDone().get()) {
            List<Article> articles = breaking.getArticles();
            setHeadline(articles.get(0));

            setRecyclerView(view, home.rvBreakingNews, articles);
        }

        if (isFetchPreferenceDone() && mCategoryCount.get() >= userPreferences.length) {
            forYouArticles = new ArrayList<>();
            for (String preference : userPreferences) {
                Category category = categoryMap.get(preference);
                forYouArticles.addAll(category.getArticles());
            }
            Collections.shuffle(forYouArticles);

            setRecyclerView(view, home.rvForYou, forYouArticles);
        }
    }

    private void setRecyclerView(@NonNull View view, RecyclerView rv, List<Article> articles) {
        rv.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(articles);
        adapter.setClickListener(new HomeFragmentAdapter.ClickListener() {
            @Override
            public void onArticleClicked(Article article) {
                Toast.makeText(getContext(), article.getLink(), Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(adapter);
    }

    private boolean isFetchPreferenceDone() {
        for (String preferences : userPreferences) {
            Category category = categoryMap.get(preferences);
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