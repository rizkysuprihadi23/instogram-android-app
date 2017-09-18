package com.example.user.instogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    List<String> listUser;
    ArrayAdapter adapter;
    ListView lv_user;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        listUser = new ArrayList<>();
        lv_user = (ListView) findViewById(R.id.lv_user);
        adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listUser);
        //menambahkan function click di listview
        lv_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //pindah halaman ke userfeedactivity
                Intent intent = new Intent(getApplicationContext(),UserFeedActivity.class);

                //lempar parameter ke activity userfeed
                intent.putExtra("username",listUser.get(i));
                startActivity(intent);
            }
        });

        //query untuk nampilin username dari parse
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null) {
                    for (ParseUser user : objects){
                        listUser.add(user.getUsername());
                    }
                    lv_user.setAdapter(adapter);
                }
                else{
                    Toast.makeText(UserListActivity.this, "Total : 0", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Interstitial ads are requested and shown by InterstitialAd objects.
        // The first step is instantiating InterstitialAd and setting its ad unit ID. This is done in the onCreate() method of an Activity
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3416774252235764/5240731663");

        //To load an interstitial ad, call the InterstitialAd object's loadAd() method.
        // This method accepts an AdRequest object as its single parameter:
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //menambahkan aksi terhadap ad/iklan
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {

                //memunculkan iklan saat setelah selesai di load
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });
    }
}
