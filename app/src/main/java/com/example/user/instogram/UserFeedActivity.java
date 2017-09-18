package com.example.user.instogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String usernameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //ambil variable yang dikirim dari intentnta
        Intent intent = getIntent();
        usernameUser = intent.getStringExtra("username");
        Log.i("username",usernameUser);


        //mengquery data di parse untuk ditampilkan
        ParseQuery<ParseObject> query = new ParseQuery("Gambar");
        query.whereEqualTo("username",usernameUser);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if( e == null){

                    Log.i("obj",objects.toString());

                    LinearLayoutManager linierLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linierLayoutManager);

                    AdapterRecyclerView adapter = new AdapterRecyclerView(objects,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(UserFeedActivity.this, "Fetch eror" +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
