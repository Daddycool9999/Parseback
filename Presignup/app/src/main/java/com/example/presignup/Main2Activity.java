package com.example.presignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Main2Activity extends AppCompatActivity {
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
            case R.id.feed:
                Intent t=new Intent(getApplicationContext(),Feed.class);
                startActivity(t);
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
        setContentView(R.layout.activity_main2);
    }
}
