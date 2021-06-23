package com.gameandapps.collection.Fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gameandapps.collection.Activity.SoundClickActivity;
import com.gameandapps.collection.Activity.GaanaActivity;
import com.gameandapps.collection.Activity.HungamaActivity;
import com.gameandapps.collection.Activity.ReverbnationActivity;
import com.gameandapps.collection.Activity.PagalWorldActivity;
import com.gameandapps.collection.Activity.WynkMusic;
import com.gameandapps.collection.Activity.YouTubeMusicActivity;
import com.gameandapps.collection.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    View view;
    CardView button0,button1,button2,button3,button4,button5,button6,button7,button8;
    ImageView imgShare;


    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music, container, false);

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
                Intent intent = new Intent(getActivity(), GaanaActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WynkMusic.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YouTubeMusicActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SoundClickActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HungamaActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReverbnationActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PagalWorldActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp("com.jio.media.jiobeats");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openApp("com.spotify.music");
                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
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
