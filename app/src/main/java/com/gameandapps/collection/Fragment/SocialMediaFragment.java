package com.gameandapps.collection.Fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gameandapps.collection.Activity.FacebookActivity;
import com.gameandapps.collection.Activity.InstagramActivity;
import com.gameandapps.collection.Activity.LinkdinActivity;
import com.gameandapps.collection.Activity.MessangerActivity;
import com.gameandapps.collection.Activity.PinterestActivity;
import com.gameandapps.collection.Activity.SnapChatActivity;
import com.gameandapps.collection.R;
import com.gameandapps.collection.Activity.TwitterActivity;
import com.gameandapps.collection.Activity.YouTubeActivity;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SocialMediaFragment extends Fragment {

    View view;
    ImageView imgShare;
    CardView button0;
    //PublisherAdView mAdView;
    CardView button1,button2,button3,button4,button5,button6,button7,button8;


    public SocialMediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_social_media, container, false);
        button0 = view.findViewById(R.id.button0);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        imgShare = view.findViewById(R.id.share);
        //mAdView = view.findViewById(R.id.adView_social);

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareBody = "Download this Application now:-https://play.google.com/store/apps/details?id=com.gameandapps.collection&hl=en_IN&hl=en";
                String sharesub="Game and Apps Collection";
                share.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                share.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(share,"ShareVia"));
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InstagramActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TwitterActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FacebookActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp("com.whatsapp");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YouTubeActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinkdinActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PinterestActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), SnapChatActivity.class));
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), MessangerActivity.class));
            }
        });
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*PublisherAdRequest publisherAdRequest = new PublisherAdRequest.Builder().build();
        mAdView.loadAd(publisherAdRequest);*/
    }

    private void openApp(String appPackageId){
        boolean isAppInstalled = appInstalledOrNot(appPackageId);
        if(isAppInstalled){
            Toast.makeText(getActivity(), "App Installed", Toast.LENGTH_SHORT).show();
            Intent intent = Objects.requireNonNull(getActivity()).getPackageManager().getLaunchIntentForPackage(appPackageId);
            if (intent != null) {
                startActivity(intent);
            }
        }else {
            Toast.makeText(getActivity(), "App not installed", Toast.LENGTH_SHORT).show();
            String packageName = appPackageId;
            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            startActivity(intent1);
        }
    }

    private boolean appInstalledOrNot(String uri){
        PackageManager packageManager = getActivity().getPackageManager();
        try{
            packageManager.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
