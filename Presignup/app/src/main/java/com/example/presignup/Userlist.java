
package com.example.presignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Userlist extends AppCompatActivity {

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
            case R.id.feed:
                Intent tt=new Intent(getApplicationContext(),Feed.class);
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
        setContentView(R.layout.activity_userlist);

        final ListView   lv=findViewById(R.id.lv);
        final ArrayList<String> arr=new ArrayList<String>();
        final ArrayAdapter<String> adp=new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, arr){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.GRAY);

                return view;
            }
        };

        //arr.add("KHOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

        ParseQuery<ParseUser> query=ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null) {
                    if (objects.size() > 0) {
                        for (ParseUser usr : objects) {
                            arr.add(usr.getUsername());

                        }
                        lv.setAdapter(adp);


                    }
                }

                else{

                        e.printStackTrace();
                        Log.i("ERROR",e.getLocalizedMessage()+"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");

                    }


            }
        });



    }
}