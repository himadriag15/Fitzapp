package com.m10798.fitzapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.Collections;

public class ExercisesActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ListView listView;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        listView = findViewById(R.id.lv_exercises);
        fab = findViewById(R.id.btn_new);
        db = openOrCreateDatabase("fitnessdb",MODE_PRIVATE,null);
        String muscle = getIntent().getStringExtra("muscle");
        Cursor resultSet = db.rawQuery("Select distinct name from exercises where muscle_group like '" + muscle + "'",null);
        final ArrayList<String> exercises = new ArrayList<>();
        for(resultSet.moveToFirst(); !resultSet.isAfterLast(); resultSet.moveToNext()) {
            // The Cursor is now set to the right position
            exercises.add(resultSet.getString(0));
        }
        resultSet.close();
        Log.d("cursor", exercises.toString());
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, exercises));
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent singleIntent = new Intent(ExercisesActivity.this, DetailActivity.class);
                singleIntent.putExtra("exercise", exercises.get(i));
                startActivity(singleIntent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder popDialog = new AlertDialog.Builder(ExercisesActivity.this);
                popDialog.setTitle("Set Difficulty");
                popDialog.setIcon(android.R.drawable.btn_star_big_on);
                LinearLayout linearLayout = new LinearLayout(ExercisesActivity.this);
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                final RatingBar rating = new RatingBar(ExercisesActivity.this);
                rating.setLayoutParams(lParams);
                rating.setNumStars(6);
                linearLayout.addView(rating);
                popDialog.setView(linearLayout);
                // Button OK
                popDialog.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("difficulty", String.valueOf(rating.getProgress()));
                                Intent multiIntent = new Intent(ExercisesActivity.this, DetailActivity.class);
                                multiIntent.putExtra("difficulty", rating.getProgress());
                                Collections.shuffle(exercises);
                                multiIntent.putExtra("exercises", exercises);
                                startActivity(multiIntent);
                            }
                        });
                popDialog.create();
                popDialog.show();
            }
        });
    }
}
