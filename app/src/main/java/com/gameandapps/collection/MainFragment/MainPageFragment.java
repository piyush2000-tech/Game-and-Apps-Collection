package com.gameandapps.collection.MainFragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gameandapps.collection.Activity.GoogleActivity;
import com.gameandapps.collection.Activity.TicTacMain;
import com.gameandapps.collection.Fragment.BrainFragment;
import com.gameandapps.collection.Fragment.MusicFragment;
import com.gameandapps.collection.Fragment.OnlineFragment;
import com.gameandapps.collection.R;
import com.gameandapps.collection.Fragment.SocialMediaFragment;
import com.gameandapps.collection.Fragment.TutorialFragment;
import com.gameandapps.collection.Fragment.ShoppingFragment;
import com.google.android.gms.ads.AdView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment /*implements RewardedVideoAdListener*/ {
    //private RewardedVideoAd mRewardedVideoAd;
    //PublisherInterstitialAd publisherInterstitialAd;
    AdView mAdView;
    View view;
    private CardView button0;
    private CardView button1;
    //private CardView button2;
    private CardView button3;
    private CardView button4;
    private CardView button5;
    private CardView button6;
    private CardView button7;
    private CardView button8;
    private ImageView imgShare;
    private RelativeLayout gridLayout;
    ImageView img1,img2,img3,img4;
    Fragment fragment;


    public MainPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_page, container, false);
        //mAdView = view.findViewById(R.id.adView);
        gridLayout = view.findViewById(R.id.gridLayoutMain);
        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);
        button0 = view.findViewById(R.id.button0);
        button1 = view.findViewById(R.id.button1);
        //button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        imgShare = view.findViewById(R.id.share);

       // ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "Download this Application now:-https://play.google.com/store/apps/details?id=com.gameandapps.collection&hl=en_IN&hl=en";
                String sharesub="Game and Apps Collection";
                intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(intent,"ShareVia"));
            }
        });


        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    /*if (publisherInterstitialAd.isLoaded()) {
                        publisherInterstitialAd.show();
                    }*/
                    Intent intent = new Intent(getActivity(), TicTacMain.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                    //getActivity().overridePendingTransition(0,0);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("ERROR is",""+e);
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(),BrainActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);*/
                fragment = new BrainFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment,fragment.getClass().getSimpleName())
                        .commit();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getContext(),SocialMediaActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);*/
                fragment = new SocialMediaFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
                //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));
                ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment,fragment.getClass().getSimpleName())
                        .commit();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
                //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5722")));
                ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
                fragment  = new OnlineFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment,fragment.getClass().getSimpleName())
                        .commit();
            }
        });

        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
                //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EBE91E63")));
                ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
                fragment  = new TutorialFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment,fragment.getClass().getSimpleName())
                        .commit();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try{
                    if(publisherInterstitialAd.isLoaded()){
                        publisherInterstitialAd.show();
                    }
                }catch(Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }*/
                Intent intent = new Intent(getActivity(), GoogleActivity.class);
                startActivity(intent);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
                //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
                fragment = new ShoppingFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment,fragment.getClass().getSimpleName())
                        .commit();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
                //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
                fragment = new MusicFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment,fragment.getClass().getSimpleName())
                        .commit();
            }
        });

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        PublisherAdView adView = new PublisherAdView(getActivity());
        adView.setAdSizes(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-6583537000510578/3754195026");


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        publisherInterstitialAd = new PublisherInterstitialAd(Objects.requireNonNull(getActivity()));
        publisherInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/5224354917");
        publisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        publisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Error ADS",""+errorCode);
                publisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                publisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
            }
        });

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();*/
    }


    /*private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("/6499/example/rewarded-video",
                new PublisherAdRequest.Builder().build());
    }

    public void onRewarded(RewardItem reward) {
        *//*Toast.makeText(getActivity(), "onRewarded! currency: " + reward.getType() + "  amount: " +
                reward.getAmount(), Toast.LENGTH_SHORT).show();*//*
        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(getActivity(), "onRewardedVideoAdLeftApplication",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(getActivity(), "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(getActivity(), "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(getActivity(), "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(getActivity(), "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        Toast.makeText(getActivity(), "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onResume() {
        //mRewardedVideoAd.resume(getActivity());
        super.onResume();
    }

    @Override
    public void onPause() {
        //mRewardedVideoAd.pause(getActivity());
        super.onPause();
    }

    @Override
    public void onDestroy() {
        //mRewardedVideoAd.destroy(getActivity());
        super.onDestroy();
    }

}
