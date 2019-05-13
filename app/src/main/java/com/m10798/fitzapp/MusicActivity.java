package com.m10798.fitzapp;




import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;

import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.FieldPosition;
import java.util.ArrayList;
import java.lang.String;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MusicActivity extends Activity {
    Button playBtn;
    SeekBar positionBar;
    SeekBar volumeBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    MediaPlayer mp;
    int totalTime;


    private ListView mainList;
    protected Object mActionMode;
    private String listItem = "";
    ArrayAdapter<String> adapter;

    private static int selectedItem = -1;
    String[] listContent = {"Godfather Theme", "Perfect Strangers", "IPhone Notification", "Tera Ghata"};
    int a;

    private final int[] resID = {R.raw.godfather_theme,R.raw.perfect_strangers,R.raw.iphone_notification,R.raw.teraghata};

    private  ArrayList list = new ArrayList();


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music);

        for (int i = 0; i < listContent.length ; ++i) {
            list.add(listContent[i]);
        }



// Initializing variables

        final SeekBar sk=new SeekBar(getApplicationContext());
        mp = new MediaPlayer();
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();
        sk.setMax(mp.getDuration());

        mainList = (ListView) findViewById(R.id.listView1);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
//

        mainList.setAdapter(adapter);


        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> adapterView, View view,

                                    int position, long id) {
                playSong(position);
//                Intent intent=new Intent(getApplicationContext(), Main2Activity.class);
//
//                String pos=Integer.toString(position);
//                intent.putExtra("pos", position);
//                startActivity(intent);
            }

        });

        final Handler mSeekbarUpdateHandler = new Handler();
        Runnable mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                sk.setProgress(mp.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);
            }
        };

        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("long clicked", "pos" + " " + position);
                if (mActionMode != null) {
                    return false;
                }
                selectedItem = -1;
                selectedItem = position;
                listItem = (String) list.get(selectedItem);
                a=position;
                mActionMode=  startActionMode(mActionModeCallback) ;
                view.setSelected(true);
                return true;
            }
        });



    }
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("");
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.mymenu, menu);
            return true;

        }

        public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
            return false;
        }

        public void onDestroyActionMode(ActionMode arg0) {
            mActionMode = null;
            selectedItem = -1;
        }

        public boolean onActionItemClicked(ActionMode mode, final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:

                    list.remove(a);
                    Toast.makeText(getApplicationContext(), "Song Deleted", Toast.LENGTH_SHORT).show();

                    mode.finish();

                    break;

                case R.id.item2:
//
//
                    list.clear();
                    Toast.makeText(getApplicationContext(), "All songs Deleted", Toast.LENGTH_SHORT).show();
//                    mainList.setAdapter(adapter);
                    mode.finish();

                    break;

            }
            return true;
        }
    };


    public void playSong(int songIndex) {

// Play song

        mp.reset();// stops any current playing song

        mp = MediaPlayer.create(getApplicationContext(), resID[songIndex]);// create's new media player with song


        mp.start(); // starting mediaplayer

    }


    @Override

    public void onDestroy() {

        super.onDestroy();

        mp.release();

    }






}


