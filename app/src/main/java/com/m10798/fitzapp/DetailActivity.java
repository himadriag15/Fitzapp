package com.m10798.fitzapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class DetailActivity extends Activity {
    SQLiteDatabase db;
    TextView nameTextView;
    TextView repsTextView;
    GifImageView img1;
    GifImageView img2;
    Button help;
    Button next;
    TextView timerText;
    String exercise;
    ArrayList<String> exercises;
    int difficulty=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nameTextView = findViewById(R.id.tv_title);
        repsTextView = findViewById(R.id.tv_reps);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        help = findViewById(R.id.btn_help);
        next = findViewById(R.id.btn_next);
        timerText = findViewById(R.id.tv_timer);
        final CountDownTimer countDownTimer;
        db = openOrCreateDatabase("fitnessdb",MODE_PRIVATE,null);
        if(getIntent().hasExtra("exercise")){
            exercise = getIntent().getStringExtra("exercise");
        }
        if(getIntent().hasExtra("exercises")){
            exercises = getIntent().getStringArrayListExtra("exercises");
            difficulty = getIntent().getIntExtra("difficulty",1);
            Log.d("difficulty", String.valueOf(difficulty));
            exercise = exercises.get(0);
            exercises.remove(0);
            timerText.setVisibility(View.VISIBLE);
            countDownTimer = new CountDownTimer((30) * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timerText.setText("Time remaining "+String.valueOf(millisUntilFinished / 1000)+"s");
                }
                public void onFinish() {
                    timerText.setText("Done");
                }
            };
            countDownTimer.start();
            next.setVisibility(View.VISIBLE);
            if(exercises.isEmpty()){
                next.setText("Finish");
            }
        }
        Cursor resultSet = db.rawQuery("Select distinct name, reps, image, info from exercises where name like '" + exercise + "'",null);
        resultSet.moveToFirst();
        String name = resultSet.getString(resultSet.getColumnIndex("name"));
        Integer reps = resultSet.getInt(resultSet.getColumnIndex("reps"));
        String imgres = resultSet.getString(resultSet.getColumnIndex("image"));
        final String info = resultSet.getString(resultSet.getColumnIndex("info"));
        resultSet.close();
        Log.d("cursor", name);
        nameTextView.setText(name);
        repsTextView.setText("Reps: "+String.valueOf(reps+difficulty));
        int img1resid=getResources().getIdentifier(imgres+"1", "drawable", getPackageName());
        int img2resid=getResources().getIdentifier(imgres+"2", "drawable", getPackageName());
        img1.setImageResource(img1resid);
        img2.setImageResource(img2resid);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                dialog.setMessage(info);
                dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent;
                if(exercises.isEmpty()){
                    nextIntent = new Intent(DetailActivity.this, MuscleGroupActivity.class);
                }
                else {
                    nextIntent = new Intent(DetailActivity.this, DetailActivity.class);
                    nextIntent.putExtra("difficulty", difficulty);
                    nextIntent.putExtra("exercises", exercises);
                }
                startActivity(nextIntent);
            }
        });
    }
}