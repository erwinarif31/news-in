package com.softwind.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwind.myapplication.databinding.ItemBreakingNewsBinding;
import com.softwind.myapplication.models.SavedArticles;
import com.softwind.myapplication.util.Content;

import java.util.List;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder> {

    private List<SavedArticles> breakingNewsList;
    public ClickListener clickListener;
    public HomeFragmentAdapter(List<SavedArticles> breakingNewsList) {
        this.breakingNewsList = breakingNewsList;
    }

    public void setClickListener (ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBreakingNewsBinding view = ItemBreakingNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentAdapter.ViewHolder holder, int position) {
        holder.onBind(breakingNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return breakingNewsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBreakingNewsBinding binding;
        public ViewHolder(@NonNull ItemBreakingNewsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(SavedArticles article) {
            binding.tvBreakingNewsTitle.setText(article.getTitle());
            binding.tvPubDate.setText(article.getDateDiff());

            if (article.getImage_url() != null) {
                Content.placeImage(binding.getRoot().getContext(), article, binding.breakingNewsImage, binding.breakingNewsImageLoading);
            } else {
                Content.setFailedResource(article);
                Content.placeImage(binding.getRoot().getContext(), article, binding.breakingNewsImage, binding.breakingNewsImageLoading);
            }

            binding.getRoot().setOnClickListener(view -> clickListener.onArticleClicked(article));
        }
    }

    public interface ClickListener {
        void onArticleClicked (SavedArticles article);
    }
}

