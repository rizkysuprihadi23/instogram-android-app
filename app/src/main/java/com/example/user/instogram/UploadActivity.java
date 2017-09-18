package com.example.user.instogram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import static android.os.Build.VERSION_CODES.M;

public class UploadActivity extends AppCompatActivity {

    //buat request code untuk ambil gambar
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //buat request code untuk buka galeri
    static final int REQUEST_IMAGE_PICK = 2;
    ImageView imageView;

    //variable untuk menampung nilai bitmap
    Bitmap bitmaps;

    EditText caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView = (ImageView) findViewById(R.id.imageViewGambar);
        caption = (EditText) findViewById(R.id.txtCaption);

    }

    //memanggil fungsi camera
    private void getCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult harus memiliki 2 parameter, yang pertama itu adalah intentnya dan yg kedua adalah requestcode nya
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //memanggil fungsi openfile
    private void getGallery(){
        Intent getGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(getGalleryIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(getGalleryIntent, REQUEST_IMAGE_PICK);
        }
    }

    //hasil trigger dari startActivityForResult yang ada di dalam method getGalery dan getCamera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //hasil trigger dari startActivityForResult getCamera
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                bitmaps = imageBitmap; //menampung nilai bitmap
            }
            else {
                Toast.makeText(this, "Jangan Dicancel", Toast.LENGTH_SHORT).show();
            }
        }

        //hasil trigger dari startActivityForResult getGalery
        if(requestCode == REQUEST_IMAGE_PICK){
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();

                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                    imageView.setImageBitmap(bitmap);
                    bitmaps = bitmap; //menampung nilai bitmap
                }
                catch (Exception e){
                    Toast.makeText(this, "Eror" +e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Jangan Dicancel", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //method untuk ambil gambar
    public void BtnCamera(View view) {
       //mengecek aplikasi berjalan di android apa, jika diatas android M maka akan dicek kembali permissionnya tapi jika dibawah M maka langsung diapnggil method get cameranya
        if(Build.VERSION.SDK_INT >= M){
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_IMAGE_CAPTURE);
            } else{
                getCamera();
            }
        }
        else {
            getCamera();
        }
   }

   //method untuk buka file/storage
   public void BtnGalery(View view){
       if(Build.VERSION.SDK_INT >= M){
           if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
               requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_IMAGE_PICK);
           } else{
               getGallery();
           }
       }
       else {
           getGallery();
       }
   }

   //method untuk upload file
   public void BtnUpload(View view){
       if(bitmaps == null){
           Toast.makeText(this, "Harap Ambil Gambar Dulu", Toast.LENGTH_SHORT).show();
           return;
       }
       //file berextensi bitmap harus dicompress dulu jadi parsefile menggunakan stream sebelum disimpan
       ByteArrayOutputStream stream = new ByteArrayOutputStream();
       bitmaps.compress(Bitmap.CompressFormat.PNG, 100, stream);
       byte[] byteArray = stream.toByteArray();

       ParseFile file = new ParseFile("image.png", byteArray);
       String captions = caption.getText().toString();

       ParseObject obj = new ParseObject("Gambar");
       obj.put("username", ParseUser.getCurrentUser().getUsername());
       obj.put("image",file);
       obj.put("caption",captions);

       obj.saveInBackground(new SaveCallback() {
           @Override
           public void done(ParseException e) {
               if(e==null){
                   Toast.makeText(UploadActivity.this, "Upload Berhasil", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   Toast.makeText(UploadActivity.this, "Upload gagal "+e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
       });
   }

   //mengecek hasil request permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCamera();
            }
        }
        else if(requestCode == REQUEST_IMAGE_PICK){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getGallery();
            }
        }
    }
}
