package com.example.presignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Uploadphoto extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                uploadphoto();
            }
        }
    }

    public void uploadphoto(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri im=data.getData();
        if(requestCode==1 && resultCode==RESULT_OK){
            if(data!=null){

                try {
                    Bitmap bp=MediaStore.Images.Media.getBitmap(this.getContentResolver(),im);

                    ByteArrayOutputStream ba=new ByteArrayOutputStream();
                    bp.compress(Bitmap.CompressFormat.PNG,100,ba);
                    byte[] baa=ba.toByteArray();
                    ParseFile fl=new ParseFile("image.png",baa);
                    ParseObject po=new ParseObject("FEED");
                    po.put("image",fl);
                    po.put("username", ParseUser.getCurrentUser().getUsername());
                    po.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Toast.makeText(Uploadphoto.this, "UPLOADED", Toast.LENGTH_SHORT).show();
                                Log.i("UPLOADED","UPLOADED");
                                Intent tt=new Intent(getApplicationContext(),Main2Activity.class);
                                startActivity(tt);
                            }
                            else
                                Toast.makeText(Uploadphoto.this,"ERROR: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                Log.i("ERROR: ",e.getLocalizedMessage()+"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                        }
                    });
                    ImageView imageView=findViewById(R.id.imageView);
                    imageView.setImageBitmap(bp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadphoto);

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }
        else
            uploadphoto();
    }
}
