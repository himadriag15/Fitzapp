package com.m10798.fitzapp;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MuscleGroupActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_group);
        listView = findViewById(R.id.lv_muscle_groups);
        db_init();
        final String[] muscles = {"Traps", "Chest", "Shoulders", "Abdominals", "Biceps", "Forearms", "Triceps", "Quads", "Calves", "Traps (mid-back)", "Lats", "Lower back", "Glutes", "Hamstrings"};
        int[] imgs = {R.drawable.traps,R.drawable.chest,R.drawable.shoulders,R.drawable.abdominals,R.drawable.biceps,R.drawable.triceps,R.drawable.forearms,R.drawable.quads,R.drawable.calves,R.drawable.trapsb,R.drawable.lats,R.drawable.lower_back,R.drawable.glutes,R.drawable.hamstrings};
        List<HashMap<String, String>> aList = new ArrayList<>();
        for (int i = 0; i < muscles.length; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("listview_title", muscles[i]);
            hm.put("listview_image", Integer.toString(imgs[i]));
            aList.add(hm);
        }
        String[] from = {"listview_image", "listview_title"};
        int[] to = {R.id.img_muscle, R.id.tv_muscle};
        listView.setAdapter(new SimpleAdapter(getBaseContext(), aList, R.layout.muscle_group_cell, from, to));
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MuscleGroupActivity.this, ExercisesActivity.class);
                intent.putExtra("muscle", muscles[i]);
                startActivity(intent);
            }
        });
    }



    private void db_init(){
        db = openOrCreateDatabase("fitnessdb",MODE_PRIVATE,null);
        BufferedReader reader;
        try{
            final InputStream file = getAssets().open("db_init.sql");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                db.execSQL(line);
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}


