package com.softwind.myapplication.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.softwind.myapplication.activity.MainActivity;
import com.softwind.myapplication.databinding.ItemBookmarkNewsBinding;
import com.softwind.myapplication.models.SavedArticles;
import com.softwind.myapplication.util.Content;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>{
    private List<SavedArticles> savedArticles;
    private ClickListener clickListener;
    private RemoveListener removeListener;

    public BookmarkAdapter(List<SavedArticles> savedArticles) {
        this.savedArticles = savedArticles;
    }

    public void setClickListener (ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setRemoveListener (RemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookmarkNewsBinding view = ItemBookmarkNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        holder.onBind(savedArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return savedArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBookmarkNewsBinding binding;
        public ViewHolder(@NonNull ItemBookmarkNewsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(SavedArticles article) {
            binding.articleTitle.setText(article.getTitle());
            binding.articleTime.setText(article.getPubDate());

            if (article.getImage_url() != null) {
                Content.placeImage(binding.getRoot().getContext(), article, binding.articleImage, binding.lavBookmark);
            } else {
//                Content.setFailedResource(article);
                Content.placeImage(binding.getRoot().getContext(), article, binding.articleImage, binding.lavBookmark);
            }

            binding.btnRemoveArticle.setOnClickListener(v -> {
                removeListener.onRemoveClicked(article);
            });

            binding.getRoot().setOnClickListener(view -> {
                clickListener.onArticleClicked(article);
            });
        }

    }
    public interface ClickListener {
        void onArticleClicked (SavedArticles article);
    }

    public interface RemoveListener {
        void onRemoveClicked (SavedArticles article);
    }

}
