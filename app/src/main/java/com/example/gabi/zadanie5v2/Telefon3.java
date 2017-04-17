package com.example.gabi.zadanie5v2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gabi.zadanie5v2.MainActivity;

public class Telefon3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefon3);
    }

    public void cofnij( View v )
    {
        Context context;
        context = getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
