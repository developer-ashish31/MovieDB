package com.intigral.moviedb._activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.intigral.moviedb.R;
import com.intigral.moviedb._model.MovieDataSet;
import com.intigral.moviedb._property.Const;
import com.intigral.moviedb._property.CustomVolleyRequestQueue;

/**
 * Created by Ajay Maurya on 10/3/2017.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private NetworkImageView mMovieBannerImg;
    private TextView mMovieTitleTv;
    private RatingBar mMovieRating;
    private TextView mMovieDescTv;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovieBannerImg = (NetworkImageView) findViewById(R.id.activity_movie_details_banner_imageview);
        mMovieTitleTv = (TextView) findViewById(R.id.activity_movie_details_movie_name_tv);
        mMovieRating = (RatingBar) findViewById(R.id.activity_movie_details_rating_bar);
        mMovieDescTv = (TextView) findViewById(R.id.activity_movie_details_movie_desc_tv);

        mImageLoader = CustomVolleyRequestQueue.getInstance(this).getImageLoader();

        if (getIntent() != null) {
            MovieDataSet movieDataSet = getIntent().getParcelableExtra(Const.MOVIE_DATA);
            if (movieDataSet != null)
                setDataToVeiw(movieDataSet);
        }
    }

    /**
     * this method set the movie details to the view.
     *
     * @param movieDataSet takes movie details object
     */
    private void setDataToVeiw(MovieDataSet movieDataSet) {
        try {
            String posterImageUrl = Const.LARGE_IMAGE_BASE_URL + movieDataSet.getPoster_path().toString();
            posterImageUrl = posterImageUrl.replaceAll("\\s+", "");
            mImageLoader.get(posterImageUrl, ImageLoader.getImageListener(mMovieBannerImg, R.drawable.large_default_image, R.drawable.large_default_image));
            mMovieBannerImg.setImageUrl(posterImageUrl, mImageLoader);
            mMovieDescTv.setText(movieDataSet.getOverview());
            mMovieTitleTv.setText(movieDataSet.getTitle());
            mMovieRating.setRating(movieDataSet.getVote_average());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
