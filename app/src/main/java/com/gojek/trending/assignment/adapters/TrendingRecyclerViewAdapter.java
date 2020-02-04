package com.gojek.trending.assignment.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gojek.trending.assignment.R;
import com.gojek.trending.assignment.databinding.ItemTrendingBinding;
import com.gojek.trending.assignment.model.Repository;
import com.gojek.trending.assignment.network.TrendingVolleyRequestQueue;

import java.util.List;

public class TrendingRecyclerViewAdapter
        extends RecyclerView.Adapter<TrendingRecyclerViewAdapter.ViewHolder> {

    private static ImageLoader imageLoader;

    private final List<Repository> repositories;
    private final OnItemClickListener listener;

    private int mExpandedPosition = -1;
    private static Context context;

    public TrendingRecyclerViewAdapter(Context mContext, List<Repository> repositories, OnItemClickListener listener) {
        this.context = mContext;
        this.repositories = repositories;
        this.listener = listener;
        imageLoader = TrendingVolleyRequestQueue.getInstance(mContext).getImageLoader();
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.repository = repositories.get(position);
        holder.binding.setRepository(repositories.get(position));
        final boolean isExpanded = position == mExpandedPosition;
        holder.binding.details.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.binding.shadow.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.binding.separator.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
        holder.view.setActivated(isExpanded);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                if (null != listener) {
                    listener.onItemClick(holder.repository);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    @BindingAdapter("imageUrl")
    public static void getAvatar(View view, String imageUrl) {
        NetworkImageView imageView = (NetworkImageView) view;
        imageView.setImageUrl(imageUrl, imageLoader);
    }

    @BindingAdapter("languageColor")
    public static void getLanguageColor(View view, String languageColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && languageColor != null) {
            Drawable tempDrawable = context.getResources().getDrawable(R.drawable.dynamic_color_circle);
            GradientDrawable shapeDrawable = (GradientDrawable) tempDrawable;
            shapeDrawable.setColor(Color.parseColor(languageColor));
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(tempDrawable, null, null, null);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        private ItemTrendingBinding binding;

        public Repository repository;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            binding = DataBindingUtil.bind(view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Repository item);
    }


}