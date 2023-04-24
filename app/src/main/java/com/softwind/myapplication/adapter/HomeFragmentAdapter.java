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
import com.softwind.myapplication.databinding.ItemBreakingNewsBinding;
import com.softwind.myapplication.models.SavedArticles;

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
//                Glide.with(itemView.getContext()).load(article.getImage_url()).into(binding.breakingNewsImage);
                Glide.with(itemView.getContext()).load(article.getImage_url()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        Toast.makeText(itemView.getContext(), "Oops. There's a problem with your network!", Toast.LENGTH_SHORT).show();
                        binding.breakingNewsImageLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.breakingNewsImageLoading.setVisibility(View.GONE);
                        return false;
                    }

                }).into(binding.breakingNewsImage);
            }

            binding.getRoot().setOnClickListener(view -> clickListener.onArticleClicked(article));
        }
    }

    public interface ClickListener {
        void onArticleClicked (SavedArticles article);
    }
}

