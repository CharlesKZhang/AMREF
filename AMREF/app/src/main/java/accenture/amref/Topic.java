package accenture.amref;
/**
 * Author: Charles Zhang
 * date: 1/14/15
 * Purpose: Creating list of topics activity
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import net.lingala.zip4j.core.ZipFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class Topic extends ActionBarActivity {
    String TAG = "Topic Listing Activity";
    private ArrayList<Message> db, db2;
    private ReadFile rf1, rf2;
    private SharedPreferences mPrefs;
    private int index1, index2;
    private Context context;
    private Intent intent1M, intent1Q, intentHome, intentSearch;


    protected void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        //initialize sharedpreferences
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Initializing url for download and context
        final Context context = this;
        final String _url = "https://googledrive.com/host/0B_vF8i_jicMSfmdWdjNfMFE3dHRTSU9TZkt5bTFyY2h6ZFRTWWdGU3RUVVM4T21mSDdGbmc/Topic1Audio.zip";
        //initialize dbs
        db = new ArrayList<Message>();
        db2 = new ArrayList<Message>();

        //download audio files
        File download = new File(context.getFilesDir().getPath() + "/download.zip");
        if (!download.exists()) {
            Thread a = new Thread() {
                public void run() {
                    try {
                        URL url = new URL(_url);
                        URLConnection connection = url.openConnection();
                        connection.connect();
                        File file = new File(context.getFilesDir().getPath() + "/download.zip");
                        FileUtils.copyURLToFile(url, file);
                        ZipFile zipFile = new ZipFile(file);
                        zipFile.extractAll(context.getFilesDir().getPath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            a.start();

            //allowing for download time
            Toast.makeText(getApplicationContext(), "Please wait for audio download", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Please wait for audio download.", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Still downloading.", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Still downloading..", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Still downloading...", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Please be patient", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), ".", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "..", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "=)", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), ".", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "..", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Almost there.", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Almost there..", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Almost there...", Toast.LENGTH_LONG).show();
        }
    }
    protected void onStart() {
        //Log.d(TAG, "onStart");
        super.onStart();}
    protected void onResume() {
        //Log.d(TAG, "oncResume");
        super.onResume();
        //initialize Intents
        index1 = getIntent().getIntExtra("INDEX1", 0);
        index2 = getIntent().getIntExtra("INDEX2", 0);
        if(index1 == 0 && index2 == 0){
            index1 = mPrefs.getInt("index1",0);
            index2 = mPrefs.getInt("index2",0);
        }

        intent1M = new Intent(this, Demo.class);
        intent1Q = new Intent(this, Quiz.class);
        intentHome = new Intent(this, HelpDemoStart.class);
        intentSearch = new Intent(this, SearchDemo.class);
        context = this;
        //initialize dbs
        db = new ArrayList<Message>();
        db2 = new ArrayList<Message>();
        rf1 = new ReadFile(Topic.this, R.raw.messages1);
        rf2 = new ReadFile(Topic.this, R.raw.messages2);
        Type type = new TypeToken<ArrayList<Message>>() {
        }.getType();

        if (getIntent().getIntExtra("TOPICNUM", 0) == 1) {
            db = getIntent().getParcelableArrayListExtra("db");
            Gson gson = new Gson();
            db2 = gson.fromJson(mPrefs.getString("db2", ""), type);
        } else if (getIntent().getIntExtra("TOPICNUM", 0) == 2) {
            db2 = getIntent().getParcelableArrayListExtra("db");
            Gson gson = new Gson();
            db = gson.fromJson(mPrefs.getString("db", ""), type);
        }else{
            Gson gson = new Gson();
            db2 = gson.fromJson(mPrefs.getString("db2", ""), type);
            db = gson.fromJson(mPrefs.getString("db", ""), type);
        }
        if (db == null|| db.isEmpty()) {
            db = rf1.getList();
            Toast.makeText(getApplicationContext(), "Please wait for Topic 1 download", Toast.LENGTH_LONG).show();
        }
        if (db2 == null || db2.isEmpty()) {
            db2 = rf2.getList();
            Toast.makeText(getApplicationContext(), "Please wait for Topic 2 download", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Now, you may select your topic", Toast.LENGTH_SHORT).show();
        }
        //Read in messages only if dbs don't have a value
        //if(db != null && db2 != null && !db.isEmpty() && !db2.isEmpty()) {

            final SharedPreferences.Editor ed = mPrefs.edit();

            //adding what will happen when clicking the buttons- going to the next activity
            try {
                View.OnClickListener handler = new View.OnClickListener() {
                    public void onClick(View v) {
                        //we will use switch statement and just
                        //get the button's id to make things easier
                        switch (v.getId()) {

                            case R.id.button1: //new topic will be shown

                                if (db.get(index1).getType().equals("Q")) {
                                    intent1Q.putExtra("INDEX1", index1);
                                    intent1Q.putExtra("INDEX2", index2);
                                    intent1Q.putParcelableArrayListExtra("db", db);
                                    intent1Q.putExtra("Context", context.getFilesDir().getPath());
                                    intent1Q.putExtra("TOPICNUM", 1);
                                    startActivity(intent1Q);
                                } else {
                                    intent1M.putExtra("INDEX1", index1);
                                    intent1M.putExtra("INDEX2", index2);
                                    intent1M.putParcelableArrayListExtra("db", db);
                                    intent1M.putExtra("Context", context.getFilesDir().getPath());
                                    intent1M.putExtra("TOPICNUM", 1);
                                    startActivity(intent1M);
                                }
                                break;
                            case R.id.button2: //new activity

                                if (db2.get(index2).getType().equals("Q")) {
                                    intent1Q.putExtra("INDEX1", index1);
                                    intent1Q.putExtra("INDEX2", index2);
                                    intent1Q.putParcelableArrayListExtra("db", db2);
                                    intent1Q.putExtra("Context", context.getFilesDir().getPath());
                                    intent1Q.putExtra("TOPICNUM", 2);
                                    startActivity(intent1Q);
                                } else {
                                    intent1M.putExtra("INDEX1", index1);
                                    intent1M.putExtra("INDEX2", index2);
                                    intent1M.putParcelableArrayListExtra("db", db2);
                                    intent1M.putExtra("Context", context.getFilesDir().getPath());
                                    intent1M.putExtra("TOPICNUM", 2);
                                    startActivity(intent1M);
                                }
                                break;
                            case R.id.button3: //new activity
                                //TODO Create Intent for new activity for HELP Features and start activity
                                //finish();
                                break;
                            case R.id.button4: //new activity
                                //TODO Create Intent for new activity for HELP ABOUT and start activity
                                //finish();
                                break;
                            case R.id.button5: //new activity
                                //TODO Create Intent for new activity for HELP Features and start activity
                                //finish();
                                break;
                            case R.id.button6: //new activity
                                //TODO Create Intent for new activity for HELP ABOUT and start activity
                                //finish();
                                break;
                            case R.id.button7: //new activity
                                //TODO Create Intent for new activity for HELP Features and start activity
                                //finish();
                                break;
                            case R.id.button8: //new activity
                                //TODO Create Intent for new activity for HELP ABOUT and start activity
                                //finish();
                                break;
                            case R.id.button9: //new activity
                                //TODO Create Intent for new activity for HELP Features and start activity
                                //finish();
                                break;
                            // case R.id.title:
                            //   startActivity(indexHome);
                            //&    break;
                            case R.id.search:
                                startActivity(intentSearch);
                                break;
                        }
                    }
                };

                //we will set the listeners
                findViewById(R.id.button1).setOnClickListener(handler);
                findViewById(R.id.button2).setOnClickListener(handler);
                findViewById(R.id.button3).setOnClickListener(handler);
                findViewById(R.id.button4).setOnClickListener(handler);
                findViewById(R.id.button5).setOnClickListener(handler);
                findViewById(R.id.button6).setOnClickListener(handler);
                findViewById(R.id.button7).setOnClickListener(handler);
                findViewById(R.id.button8).setOnClickListener(handler);
                findViewById(R.id.button9).setOnClickListener(handler);
                //findViewById(R.id.title).setOnClickListener(handler);
                findViewById(R.id.search).setOnClickListener(handler);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //super.onSaveInstanceState(savedInstanceState);
        }
    //}

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause()");
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("index1", index1);
        ed.putInt("index2", index2);
        Gson j1 = new Gson();
        final String jdb2 = j1.toJson(db2);
        final String jdb1 = j1.toJson(db);
        ed.putString("db2", jdb2);
        ed.putString("db", jdb1);
        ed.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
