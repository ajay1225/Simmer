package com.example.lenovo.simmer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.simmer.dummy.DummyContent;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "resultStep";
    public SimpleExoPlayer exoPlayer;
    public SimpleExoPlayerView exoPlayerView;
    public long  playerPosition;
    public Boolean whenReady=true;
    RecipeDetails.StepDetails resultStep=null;
    public String videoURL="";

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
       resultStep=(RecipeDetails.StepDetails) getActivity().getIntent().getSerializableExtra("resultStep");

        exoPlayerView = rootView.findViewById(R.id.id_video_view);
        ImageView thumbnailImage=rootView.findViewById(R.id.id_thumbNail);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout =  activity.findViewById(R.id.toolbar_layout);
        if (getArguments().getSerializable("resultStep")!=null) {
            resultStep= (RecipeDetails.StepDetails) getArguments().getSerializable("resultStep");
            if (appBarLayout!=null) {
                appBarLayout.setTitle(resultStep.getShortDescription());
            }
            ((TextView) rootView.findViewById(R.id.id_step_details)).setText(resultStep.getDescription());

            if(resultStep.getVideoURL()!=""&&resultStep.getVideoURL()!=null) {
                videoURL=resultStep.getVideoURL();
                settingVideo();
            }
            else if(resultStep.getThumbnailURL().contains(".mp4")){
                videoURL=resultStep.getThumbnailURL();
                    settingVideo();
                }
                else if(resultStep.getThumbnailURL()!=null&&resultStep.getThumbnailURL()!=""){
                    Picasso.with(getActivity()).load(resultStep.getThumbnailURL()).into(thumbnailImage);
                    exoPlayerView.setVisibility(View.INVISIBLE);
                Toast.makeText(activity, "No video for this step", Toast.LENGTH_SHORT).show();
                }
        }




        if(savedInstanceState!=null){
            playerPosition=savedInstanceState.getLong("position");
            exoPlayer.seekTo(playerPosition);
            whenReady=savedInstanceState.getBoolean("whenReady");
            exoPlayer.setPlayWhenReady(whenReady);

        }
        return rootView;
    }
    public void settingVideo() {
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            Log.i("videoURL", videoURL);
            exoPlayerView.setPlayer(exoPlayer);
            Uri videoURI = Uri.parse(videoURL);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
            exoPlayerView.setVisibility(View.VISIBLE);
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(playerPosition);
            exoPlayer.setPlayWhenReady(true);
            LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) exoPlayerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
        } catch (Exception e) {
            Log.e("MainAcvtivity", " exoplayer error " + e.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(exoPlayer!=null){
            outState.putLong("position",exoPlayer.getCurrentPosition());
            outState.putBoolean("whenReady",exoPlayer.getPlayWhenReady());
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
            exoPlayer=null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer(){
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }
}
