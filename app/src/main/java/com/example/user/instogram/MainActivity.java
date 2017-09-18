package com.example.user.instogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    Button btnlogin;
    TextView textViewOptional;

    TextView baru;

    String status = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        textViewOptional = (TextView) findViewById(R.id.txtoptional);

        //konfigurasi parse
        ParseInitial();

        //mengecek apakah user sudah login atau belum, kalo sudah login langsung pindah halaman
        if(ParseUser.getCurrentUser() != null){
            gotohome();
        }

        //set status default saat pertama kali di running
        StatusInitial();

        //mengecek status saat textview di click
        textViewOptional.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatusInitial();
            }
        });

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3416774252235764~6473034236");
    }

    private void StatusInitial() {
        if(status == "login"){
            status = "register";
            textViewOptional.setText("Alreadey have account, Login Here...!!!");
            btnlogin.setText("Register");
        }
        else {
            status = "login";
            textViewOptional.setText("Not have acount?, Sign Up Here...");
            btnlogin.setText("Login");
        }
    }

    private void ParseInitial() {
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("9c344d161a974bb20f0cbb5e869a455e2bacb9ef")
                .server("http://ec2-52-14-11-143.us-east-2.compute.amazonaws.com:80/parse")
                .build()
        );
}


    public void btnsaved(View view){

        //parsequery digunakan untuk ngread object, karena kita bisa menambahkan kondisi/where
        //====================================================================================================
        //ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("NationalityIndonesia");

        //kalo ingin bikin atau simpan object baru, langsung bikin aja objectnya pake parse object karena di parseobject tidak bisa menambahkan kondisi
        //====================================================================================================
        //        ParseObject obj = new ParseObject("NationalityIndonesia"); //dibuatin langsung table nationality
        //        Memasukan data ke object
        //        obj.put("name","Rizky");
        //        obj.put("nationality","Indonesia");
        //        obj.saveInBackground(new SaveCallback() {
        //            //savedinbackground adalah task untuk object baru di background
        //            //callback listener atau savecallback akan dipanggil kalo sesuatu sudah selesai dijalankan
        //            @Override
        //            public void done(ParseException e) {
        //                //ngecek berhasil atau ngga save datanya
        //                if(e == null){
        //                    Toast.makeText(MainActivity.this,"Berhasil",Toast.LENGTH_SHORT).show();
        //                }
        //                else{
        //                    Toast.makeText(MainActivity.this, "Gagal --" +e.getMessage(), Toast.LENGTH_SHORT).show();
        //                }
        //            }
        //        });
        //====================================================================================================


        //memanggil method saat button diclick berdasarkan status buttonnya
        if(status == "login"){
            Login();
        }
        else {
            SignUp();
        }
    }

    private void Login() {
        final String user_name = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        ParseUser.logInInBackground(user_name, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    gotohome();
                    Toast.makeText(MainActivity.this, "Berhasil Login " +user.getUsername(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SignUp() {
        ParseUser user = new ParseUser();
        user.setUsername(editTextUsername.getText().toString());
        user.setPassword(editTextPassword.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    gotohome();
                    Toast.makeText(MainActivity.this, "Berhasil Daftar ", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Gagal Daftar--" +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void gotohome(){
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }


}
