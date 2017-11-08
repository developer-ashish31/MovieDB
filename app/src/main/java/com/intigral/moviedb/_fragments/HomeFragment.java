package com.intigral.moviedb._fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.intigral.moviedb.R;
import com.intigral.moviedb._activities.MovieDetailsActivity;
import com.intigral.moviedb._adapters.MoviesAdapter;
import com.intigral.moviedb._httpengine.GetApiEngine;
import com.intigral.moviedb._init.RequestResponseGet;
import com.intigral.moviedb._model.MovieDataSet;
import com.intigral.moviedb._property.Const;
import com.intigral.moviedb._property.RequestType;
import com.intigral.moviedb._property.UtilitesData;
import com.intigral.moviedb.listener.OnLoadMoreListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Ajay Maurya on 10/3/2017.
 */

public class HomeFragment extends Fragment implements RequestResponseGet, MoviesAdapter.OnRecycleViewItemClickListener {
    private RecyclerView mPopularMovieRecyclerView;
    private GetApiEngine apiEngine;
    private Context mContext;
    private MoviesAdapter mPopularMoviesAdapter;
    private MoviesAdapter mTopRatedMoviesAdapter;
    private MoviesAdapter mRevenueMoviesAdapter;
    private ArrayList<MovieDataSet> mPopularMovieList;
    private ArrayList<MovieDataSet> mTopRatedMovieList;
    private ArrayList<MovieDataSet> mRevenueMovieList;
    private RecyclerView mTopRatedMoviesRecyclerView;
    private RecyclerView mRevenueMoviesRecyclerView;
    private TextView mPopularTv;
    private TextView mTopRatedTv;
    private TextView mRevenueTv;
    private LinearLayout mRootLayout;
    private int PAGE = 1;
    private boolean loading;
    private int TOP_RATED_PAGE = 1;
    private int REVENUE_PAGE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        mContext = getActivity();
        mPopularMovieRecyclerView = view.findViewById(R.id.popular_movie_recycler_view);
        mTopRatedMoviesRecyclerView = view.findViewById(R.id.top_rated_movie_recycler_view);
        mRevenueMoviesRecyclerView = view.findViewById(R.id.revenue_movie_recycler_view);
        mRootLayout = view.findViewById(R.id.home_fragment_root_layout);
        mPopularTv = view.findViewById(R.id.home_fragment_popular_tv);
        mTopRatedTv = view.findViewById(R.id.home_fragment_top_rated_tv);
        mRevenueTv = view.findViewById(R.id.home_fragment_revenue_tv);

        mPopularMovieList = new ArrayList<MovieDataSet>();
        hitPopularMoveAPI();
        mPopularMovieRecyclerView.setLayoutManager(new LinearLayoutManager(mContext.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        mPopularMoviesAdapter = new MoviesAdapter(mPopularMovieList, mContext, mPopularMovieRecyclerView);
        mPopularMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPopularMovieRecyclerView.setAdapter(mPopularMoviesAdapter);
        mPopularMoviesAdapter.setOnRecycleViewListener(this);

        mTopRatedMovieList = new ArrayList<MovieDataSet>();
        hitTopRatedMoveAPI();
        mTopRatedMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        mTopRatedMoviesAdapter = new MoviesAdapter(mTopRatedMovieList, mContext, mTopRatedMoviesRecyclerView);
        mTopRatedMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTopRatedMoviesRecyclerView.setAdapter(mTopRatedMoviesAdapter);
        mTopRatedMoviesAdapter.setOnRecycleViewListener(this);

        mRevenueMovieList = new ArrayList<MovieDataSet>();
        hitRevenueMoveAPI();
        mRevenueMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        mRevenueMoviesAdapter = new MoviesAdapter(mRevenueMovieList, mContext, mRevenueMoviesRecyclerView);
        mRevenueMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRevenueMoviesRecyclerView.setAdapter(mRevenueMoviesAdapter);
        mRevenueMoviesAdapter.setOnRecycleViewListener(this);

        mPopularMoviesAdapter.setLoaded();
        mPopularMoviesAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPopularMovieList.add(null);
                hitPopularMoveAPI();

            }
        });
        mTopRatedMoviesAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mTopRatedMovieList.add(null);
                hitTopRatedMoveAPI();

            }
        });
        mRevenueMoviesAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRevenueMovieList.add(null);
                hitRevenueMoveAPI();

            }
        });
        return view;
    }

    /**
     * this method will get the most popular movies from the moviedb server
     */
    private void hitPopularMoveAPI() {
        apiEngine = new GetApiEngine(
                mContext,
                Const.POPULAR_MOVIE_URL + "&page=" + PAGE,
                "", Const.TYPE_OFF_REQUEST_GET_JSONOBJECT, HomeFragment.this, RequestType.POPULAR_MOVIE_REQUEST);
    }

    /**
     * this method will get the top rated movies from the moviedb server
     */
    private void hitTopRatedMoveAPI() {
        apiEngine = new GetApiEngine(
                mContext,
                Const.TOP_RATED_MOVIE_URL + "&page=" + TOP_RATED_PAGE,
                "", Const.TYPE_OFF_REQUEST_GET_JSONOBJECT, HomeFragment.this, RequestType.TOP_RATED_MOVIE_REQUEST);
    }

    /**
     * this method will get the best revenue movies from the moviedb server
     */
    private void hitRevenueMoveAPI() {
        apiEngine = new GetApiEngine(
                mContext,
                Const.REVENUE_MOVIE_URL + "&page=" + REVENUE_PAGE,
                "", Const.TYPE_OFF_REQUEST_GET_JSONOBJECT, HomeFragment.this, RequestType.REVENUE_MOVIE_REQUEST);
    }


    @Override
    public void onErrorResponseGet(VolleyError volleyError, String requestType) {
        if (volleyError instanceof NetworkError) {
            UtilitesData.showSnackBar(mRootLayout, getString(R.string.network_error), getActivity());
        } else if (volleyError instanceof ServerError) {
            UtilitesData.showSnackBar(mRootLayout, getString(R.string.server_error), getActivity());
        } else if (volleyError instanceof AuthFailureError) {
            UtilitesData.showSnackBar(mRootLayout, getString(R.string.auth_failure_error), getActivity());
        } else if (volleyError instanceof ParseError) {
            UtilitesData.showSnackBar(mRootLayout, getString(R.string.parse_error), getActivity());
        } else if (volleyError instanceof NoConnectionError) {
            UtilitesData.showSnackBar(mRootLayout, getString(R.string.no_connection_error), getActivity());
        } else if (volleyError instanceof TimeoutError) {
            UtilitesData.showSnackBar(mRootLayout, getString(R.string.timeout_error), getActivity());
        }
    }

    @Override
    public void onResponseGet(JSONObject response, String requestType) {
        responseParser(response, requestType);
    }

    @Override
    public void onResponseGet(JSONArray response, String requestType) {

    }

    @Override
    public void onResponseGet(String s, String requestType) {

    }

    /**
     * this method parse the server response on the basis of request type
     */
    private void responseParser(JSONObject response, String requestType) {
        try {
            if (requestType.equalsIgnoreCase(RequestType.POPULAR_MOVIE_REQUEST)) {
                if (response.has("results")) {
                    mPopularTv.setVisibility(View.VISIBLE);
                    if (mPopularMovieList.size() > 0) {
                        mPopularMovieList.remove(mPopularMovieList.size() - 1);
                        mPopularMoviesAdapter.notifyItemRemoved(mPopularMovieList.size());
                    }

                    JSONArray results = response.getJSONArray("results");
                    Gson gson = new Gson();
                    for (int i = 0; i < results.length(); i++) {
                        try {
                            String data = results.getJSONObject(i).toString();
                            MovieDataSet bean = gson.fromJson(data, MovieDataSet.class);
                            mPopularMovieList.add(bean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (response.has("total_results") && response.getInt("total_results") == 0) {
                    } else {
                        PAGE = PAGE + 1;
                    }
                    mPopularMoviesAdapter.notifyDataSetChanged();
                    mPopularMoviesAdapter.setLoaded();

                    if (response.has("total_pages")) {
                        if (response.getInt("total_pages") <= 0) {
                            mPopularMoviesAdapter.setLoading();
                        }
                    }

                    mPopularMoviesAdapter.notifyDataSetChanged();

                }
            } else if (requestType.equalsIgnoreCase(RequestType.TOP_RATED_MOVIE_REQUEST)) {
                if (response.has("results")) {
                    mTopRatedTv.setVisibility(View.VISIBLE);
                    if (mTopRatedMovieList.size() > 0) {
                        mTopRatedMovieList.remove(mTopRatedMovieList.size() - 1);
                        mTopRatedMoviesAdapter.notifyItemRemoved(mTopRatedMovieList.size());
                    }
                    JSONArray results = response.getJSONArray("results");
                    Gson gson = new Gson();
                    for (int i = 0; i < results.length(); i++) {
                        try {
                            String data = results.getJSONObject(i).toString();
                            MovieDataSet bean = gson.fromJson(data, MovieDataSet.class);
                            mTopRatedMovieList.add(bean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (response.has("total_results") && response.getInt("total_results") == 0) {
                    } else {
                        TOP_RATED_PAGE = TOP_RATED_PAGE + 1;
                    }
                    mTopRatedMoviesAdapter.notifyDataSetChanged();
                    mTopRatedMoviesAdapter.setLoaded();

                    if (response.has("total_pages")) {
                        if (response.getInt("total_pages") <= 0) {
                            mTopRatedMoviesAdapter.setLoading();
                        }
                    }
                    mTopRatedMoviesAdapter.notifyDataSetChanged();

                }
            } else if (requestType.equalsIgnoreCase(RequestType.REVENUE_MOVIE_REQUEST)) {
                if (response.has("results")) {
                    mRevenueTv.setVisibility(View.VISIBLE);
                    if (mRevenueMovieList.size() > 0) {
                        mRevenueMovieList.remove(mRevenueMovieList.size() - 1);
                        mRevenueMoviesAdapter.notifyItemRemoved(mRevenueMovieList.size());
                    }
                    JSONArray results = response.getJSONArray("results");
                    Gson gson = new Gson();
                    for (int i = 0; i < results.length(); i++) {
                        try {
                            String data = results.getJSONObject(i).toString();
                            MovieDataSet bean = gson.fromJson(data, MovieDataSet.class);
                            mRevenueMovieList.add(bean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (response.has("total_results") && response.getInt("total_results") == 0) {
                    } else {
                        REVENUE_PAGE = REVENUE_PAGE + 1;
                    }
                    mRevenueMoviesAdapter.notifyDataSetChanged();
                    mRevenueMoviesAdapter.setLoaded();

                    if (response.has("total_pages")) {
                        if (response.getInt("total_pages") <= 0) {
                            mRevenueMoviesAdapter.setLoading();
                        }
                    }
                    mRevenueMoviesAdapter.notifyDataSetChanged();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRecycleItemClick(int position, MovieDataSet movieDataSet) {
        Intent intent = new Intent(mContext, MovieDetailsActivity.class);
        intent.putExtra(Const.MOVIE_DATA, movieDataSet);
        startActivity(intent);
    }
}
