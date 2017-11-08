package com.intigral.moviedb._adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.intigral.moviedb.R;
import com.intigral.moviedb._model.MovieDataSet;
import com.intigral.moviedb._property.Const;
import com.intigral.moviedb._property.CustomVolleyRequestQueue;
import com.intigral.moviedb.listener.OnLoadMoreListener;

import java.util.ArrayList;


/**
 * Created by Ajay Maurya on 10/3/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ImageLoader mImageLoader;
    private ArrayList<MovieDataSet> mMoviesList;
    private Context mContext;
    private OnRecycleViewItemClickListener mListener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 5;
    private boolean loading;
    private int lastVisibleItem, totalItemCount;

    public MoviesAdapter(ArrayList<MovieDataSet> moviesList, Context context, RecyclerView recyclerView) {
        this.mMoviesList = moviesList;
        this.mContext = context;
        mImageLoader = CustomVolleyRequestQueue.getInstance(mContext).getImageLoader();
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();

                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // End has been reached
                    // Do something
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    loading = true;
                }
            }
        });

    }


    public void setOnRecycleViewListener(OnRecycleViewItemClickListener listener) {
        mListener = (OnRecycleViewItemClickListener) listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
                return new MyViewHolder(view);
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            default:
                return null;
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


    @Override
    public int getItemViewType(int position) {
        if (mMoviesList.get(position) == null) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MovieDataSet movie = mMoviesList.get(position);
        int type = getItemViewType(position);
        switch (type) {
            case VIEW_TYPE_ITEM:
                try {
                    MyViewHolder myViewHolder = (MyViewHolder) holder;
                    String posterImageUrl = Const.IMAGE_BASE_URL + movie.getPoster_path().toString();
                    posterImageUrl = posterImageUrl.replaceAll("\\s+", "");
                    mImageLoader.get(posterImageUrl, ImageLoader.getImageListener(myViewHolder.mMovieImageView, R.drawable.default_small_image, R.drawable.default_small_image));
                    myViewHolder.mMovieImageView.setImageUrl(posterImageUrl, mImageLoader);
                    myViewHolder.mMovieImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mListener != null)
                                mListener.onRecycleItemClick(position, mMoviesList.get(position));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case VIEW_TYPE_LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mMoviesList != null)
            return mMoviesList.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView mMovieImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = (NetworkImageView) itemView.findViewById(R.id.movie_imageview);
        }
    }


    public void setLoaded() {
        loading = false;
    }

    public void setLoading() {
        loading = true;
    }


    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    public interface OnRecycleViewItemClickListener {
        public void onRecycleItemClick(int position, MovieDataSet movieDataSet);
    }
}
