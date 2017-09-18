package com.example.user.instogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    int duration = 2000;
    long timeClicked = 0;
    Button keluar;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        keluar = (Button) findViewById(R.id.buttonLogout);
        keluar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
            }
        });



        //how to load an ad in the onCreate() method of an Activity
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public void onBackPressed(){
        if (doubleBackToExitPressedOnce){
            if((System.currentTimeMillis() - timeClicked) < duration){
                System.exit(1);
            }
            else{
                Toast.makeText(this, "Please click back again to exit ", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit ", Toast.LENGTH_SHORT).show();
            timeClicked = System.currentTimeMillis();
        }
    }

    //menambahkan file xml menu ke dalam layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return  super.onCreateOptionsMenu(menu);
    }


    public void keluar(){
        ParseUser.logOut();
        super.onBackPressed();
    }

    //menambahkan aksi ke dalam item menu saat dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.menuUpload){
            Intent intent = new Intent(getApplicationContext(),UploadActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.menuLogout){
            ParseUser.logOut();
            super.onBackPressed();
        }
        if(item.getItemId() == R.id.menuUser){
            Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
