package com.example.deiyv.deliveryfood;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    Button btnSingIn,btnSingUp;
    TextView tvSlogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSingIn=(Button) findViewById(R.id.singIn);

        btnSingUp=(Button) findViewById(R.id.singUp);
        tvSlogan=(TextView) findViewById(R.id.tvSlogan);

        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        tvSlogan.setTypeface(face);


        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singUp=new Intent(MainActivity.this ,SignUp.class);
                startActivity(singUp);
            }
        });

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singIn=new Intent(MainActivity.this ,SignIn.class);
                startActivity(singIn);
            }
        });
    }
}
