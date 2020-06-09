package com.example.presignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mn=getMenuInflater();
        mn.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.user:
                Intent tt=new Intent(getApplicationContext(),Userlist.class);
                startActivity(tt);
                return true;
            case R.id.photo:
                Intent ttt=new Intent(getApplicationContext(),Uploadphoto.class);
                startActivity(ttt);
                return true;
            case R.id.log:
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Log.i("SIGNED OUT","SIGNED OUT");
                        }
                        else
                            Log.i("ERROR: ",e.getLocalizedMessage()+"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                    }
                });
                Intent k=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(k);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        final LinearLayout lin=findViewById(R.id.lin);

        ParseQuery<ParseObject> pq=new ParseQuery<ParseObject>("FEED");
        pq.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        pq.addDescendingOrder("createdAt");
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0) {
                    for (ParseObject pb : objects) {
                        ParseFile pf=(ParseFile)pb.get("image");
                        pf.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e==null && data!=null) {
                                    Bitmap bb = BitmapFactory.decodeByteArray(data, 0, data.length);

                                    ImageView img = new ImageView(getApplicationContext());
                                    img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                    img.setScaleType(ImageView.ScaleType.FIT_XY);

                                    img.setPadding(5, 10, 5, 10);
                                    img.setImageBitmap(bb);
                                    lin.addView(img);

                                }
                            }
                        });
                    }
                }
            }
        });

    }
}
