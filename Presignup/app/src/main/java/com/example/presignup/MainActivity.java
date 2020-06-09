package com.example.presignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {
    Boolean sign;
    TextView tv;
    TextView us;
    TextView ps;
    ScrollView scroll;

    ConstraintLayout ll;
    TextView tv1;
    public void change(){
        Intent tt=new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(tt);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.textView2){
            if(sign==true){
                tv.setText("Log IN");
                tv1.setText("or Sign UP");
                sign=false;
            }
            else{
                tv.setText("Sign UP");
                tv1.setText("or Log IN");
                sign=true;
            }
        }
        else if(v.getId()==R.id.Layout){
            InputMethodManager im= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if(getCurrentFocus()!=null)
            im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }

    }

    public void GO(View view){
        if(sign==true){
            ParseUser user=new ParseUser();

                user.setUsername(us.getText().toString());


                user.setPassword(ps.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        Toast.makeText(MainActivity.this,"SIGNED UP", Toast.LENGTH_SHORT).show();
                        Log.i("SIGNED UP","SIGNED UP");
                        change();
                    }
                    else{
                        Toast.makeText(MainActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("ERROR:",e.getLocalizedMessage()+" ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                    }
                }
            });
        }
        else{
            ParseUser.logInInBackground(us.getText().toString(), ps.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e==null){
                        Toast.makeText(MainActivity.this,"Hello "+ user.getUsername(), Toast.LENGTH_SHORT).show();
                        Log.i("LOGGED IN","LOGGED IN");
                        change();
                    }
                    else{
                        Toast.makeText(MainActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("ERROR:",e.getLocalizedMessage()+" ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign=true;
        tv=findViewById(R.id.textView);
        tv1=findViewById(R.id.textView2);

        ll=findViewById(R.id.Layout);

        tv1.setOnClickListener(this);


        ll.setOnClickListener(this);
        //scroll.setOnClickListener(this);
        ps = (TextView) findViewById(R.id.Pass);
        us = (TextView) findViewById(R.id.User);
        ps.setOnKeyListener(this);

        if(ParseUser.getCurrentUser()!=null){
            change();
        }

    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
            GO(v);

        }
        return false;
    }
}
