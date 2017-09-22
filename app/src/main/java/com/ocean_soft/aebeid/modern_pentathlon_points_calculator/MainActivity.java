package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    public void BtnClicked(View view){
        Intent i = null;
        switch (view.getId()) {
            case R.id.newList:
                i = new Intent(getApplicationContext(), ParticipantsList.class);
                break;
            case R.id.singleEntry:
                i = new Intent(getApplicationContext(), SingleEntry.class);
                break;
            case R.id.competition:
                i = new Intent(getApplicationContext(), SingleEntry.class);
                break;
        }
        startActivity(i);

    }

    public static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(getApplicationContext());

    }

    static String ARRAY_DIVIDER = "-,-";

    public static String serialize(ArrayList<String> content){
        return TextUtils.join(ARRAY_DIVIDER, content.toArray());
    }

    public static ArrayList<String> deserialize(String content){
        return new ArrayList<String>(Arrays.asList(content.split(ARRAY_DIVIDER)));
    }



}

