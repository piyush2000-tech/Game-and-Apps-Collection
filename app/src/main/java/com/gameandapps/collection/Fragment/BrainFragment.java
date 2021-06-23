package com.gameandapps.collection.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gameandapps.collection.R;
import com.gameandapps.collection.Activity.divisionBrainActivity;
import com.gameandapps.collection.Activity.multiplyBrainActivity;
import com.gameandapps.collection.Activity.subtractBrainActivity;
import com.gameandapps.collection.Activity.sumBrainActivity;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrainFragment extends Fragment {
    View view;
    CardView button0,button1,button2,button3;
    ImageView imgShare;
    //PublisherAdView mAdView;


    public BrainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_brain, container, false);
        button0 = view.findViewById(R.id.button0);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        imgShare = view.findViewById(R.id.share);
        //mAdView = view.findViewById(R.id.adView_brain);

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
                Intent intent = new Intent(getActivity(), sumBrainActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), multiplyBrainActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), subtractBrainActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), divisionBrainActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*PublisherAdRequest publisherAdRequest = new PublisherAdRequest.Builder().build();
        mAdView.loadAd(publisherAdRequest);*/
    }
}
