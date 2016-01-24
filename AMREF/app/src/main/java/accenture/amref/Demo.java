package accenture.amref;
/**
 * Author: Charles Zhang
 * Date: 1/14/15
 * Purpose: An Activity to identify the next message in the curriculum and navigate the activity to
 * the correct activity/buttons.
 */

import android.content.Intent;
import android.app.Activity;
import android.content.SharedPreferences;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;



public class Demo extends Activity implements MediaPlayer.OnErrorListener {
    ArrayList<Message> db;
    MediaPlayer player;
    String TAG = "DEMO";
    MediaRecorder mRecorder;
    private int currentIndex, leaveIndex, topicNum, index1, index2;
    private TextView t, header;
    private boolean isPause;
    private SharedPreferences mPrefs;
    private String prevWord = "";
    private String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        header = (TextView)findViewById(R.id.backToTopic);

            //Log.i(TAG, "OnCreate");
        mPrefs = getSharedPreferences("index", 0);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("index1", 0);
        ed.putInt("index2", 0);
        ed.commit();
        //set audio, record, stop, and pause button invisible as default
        findViewById(R.id.audioButton).setVisibility(View.GONE);
        findViewById(R.id.recordButton).setVisibility(View.GONE);
        findViewById(R.id.stopButton).setVisibility(View.GONE);
        findViewById(R.id.pauseButton).setVisibility(View.GONE);

    }


    @Override
    public void onStart(){
        super.onStart();
        }

    public void onResume() {
        //Log.i(TAG, "OnResume");
        super.onResume();

        //only set the next button to be visible by default
        findViewById(R.id.audioButton).setVisibility(View.GONE);
        findViewById(R.id.recordButton).setVisibility(View.GONE);
        findViewById(R.id.stopButton).setVisibility(View.GONE);
        findViewById(R.id.pauseButton).setVisibility(View.GONE);
        findViewById(R.id.nextButton).setVisibility(View.VISIBLE);

        //initialize index
        final Intent receiveIntent = getIntent();
        final int index1 = receiveIntent.getIntExtra("INDEX1", 0);
        final int index2 = receiveIntent.getIntExtra("INDEX2", 0);
        int newIndex;
        topicNum = receiveIntent.getIntExtra("TOPICNUM",0);
        if (topicNum == 1) {
            newIndex = index1;
            header.setText(getResources().getString(R.string.topic1));
        }
        else {
            newIndex = index2;
            header.setText(getResources().getString(R.string.topic2));
        }
        currentIndex = newIndex;
        mPrefs = getSharedPreferences("index", 0);

        //get arraylist values
        db = new ArrayList<Message>();
        db = receiveIntent.getParcelableArrayListExtra("db");
        while (db == null || db.isEmpty()){try {
            wait(10);
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        //gets the last index prior to teh app pausing
        if(topicNum ==1)
            leaveIndex = mPrefs.getInt("index1", currentIndex);
        else
            leaveIndex = mPrefs.getInt("index2", currentIndex);
        if(leaveIndex > currentIndex && leaveIndex!= db.size()){
            currentIndex = leaveIndex;
        }

        //initialize intent
        final Intent topicIntent = new Intent(this, Topic.class);
        final Intent intent = new Intent(this, Quiz.class);

        //initialize text view
        t = (TextView)findViewById(R.id.txt_msg);
        t.setMovementMethod(new ScrollingMovementMethod());

        //initialize media player
        final String path = receiveIntent.getStringExtra("Context");
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setScreenOnWhilePlaying(true);
        player.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.stop();
                mp.reset();
                return true;
            }
        });
        //variable to tell if audio is paused
        isPause = false;

        //will not have to check if the currentindex is a quiz since this would never occur here
        if(currentIndex == 0)
        {
            t.append("\n" + "The following is a demonstration of how HELP content is currently delivered to a basic phone. Please use the buttons at the bottom of the screen to navigate through the topic. The 'Next' button will move you onto the next activity. The " +
                    "'Simulate Call' button will appear when it's time for the user to dial to listen to the audio content. If you click the button, you will hear the audio."
                    + "\n\n\n");
        }
        if(currentIndex!=0) {
            //if previous message was a quiz, then go into this loop
            if (db.get(currentIndex - 1).getType().equals("Q")) {

                if (db.get(currentIndex).getType().equals("M")) {
                    //Log.i(TAG, "message is a txt message");
                    t.append("\n" + db.get(currentIndex).getTxt() + "\n");
                    currentIndex++;
                    findViewById(R.id.nextButton).setVisibility(View.VISIBLE);
                } else if (db.get(currentIndex).getType().equals("Audio")) {
                    //Log.i(TAG, "message is an audio type");
                    findViewById(R.id.audioButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.nextButton).setVisibility(View.INVISIBLE);

                }
            }
        }
        //listening for the buttons
        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getId() == R.id.nextButton && currentIndex >= db.size()){
                    findViewById(R.id.nextButton).setVisibility(View.INVISIBLE);
                    if(receiveIntent.getIntExtra("TOPICNUM", 0) == 1){
                        topicIntent.putExtra("INDEX1", 0);
                        topicIntent.putExtra("INDEX2", index2);
                        topicIntent.putExtra("TOPICNUM", 1);
                    }
                    else{
                        topicIntent.putExtra("INDEX1", index1);
                        topicIntent.putExtra("INDEX2", 0);
                        topicIntent.putExtra("TOPICNUM", 2);
                    }
                    topicIntent.putParcelableArrayListExtra("db",db);
                    startActivity(topicIntent);
                }

                else if(v.getId() == R.id.nextButton && currentIndex < db.size()) {
                    //Log.i(TAG, "next button has been clicked");
                    findViewById(R.id.audioButton).setVisibility(View.GONE);
                    findViewById(R.id.recordButton).setVisibility(View.GONE);
                    findViewById(R.id.stopButton).setVisibility(View.GONE);
                    findViewById(R.id.pauseButton).setVisibility(View.GONE);

                    if(player.isPlaying()){
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        player.reset();
                        currentIndex++;
                    }
                    //if type of the db is q, then start Quiz activity and send over the ArrayList and index
                    if(db.get(currentIndex).getType().equals("Q")){
                        //Log.i(TAG, "message is quiz!");
                        if(topicNum == 1){
                            intent.putExtra("INDEX1", currentIndex);
                            intent.putExtra("INDEX2", index2);
                            intent.putExtra("TOPICNUM", 1);
                        }
                        else{
                            intent.putExtra("INDEX1", index1);
                            intent.putExtra("INDEX2", currentIndex);
                            intent.putExtra("TOPICNUM", 2);
                        }
                        intent.putParcelableArrayListExtra("db", db);
                        intent.putExtra("Context",path);
                        startActivity(intent);
                    }
                    else if(db.get(currentIndex).getType().equals("M")){
                        //Log.i(TAG, "message is a txt message");
                        t.append("\n" + db.get(currentIndex).getTxt() + "\n");
                        currentIndex++;
                    }
                    else if(db.get(currentIndex).getType().equals("Audio")){
                        //Log.i(TAG, "message is an audio type");
                        findViewById(R.id.audioButton).setVisibility(View.VISIBLE);
                        String currentWord = db.get(currentIndex).getTxt();

                        if (prevWord.equals(currentWord)) {
                            Toast.makeText(getApplicationContext(), "You need to click the 'Simulate Call' button to progress to the next audio clip", Toast.LENGTH_SHORT).show();
                        }else{
                            t.append("\n" + db.get(currentIndex).getTxt() + "\n");}
                            prevWord = db.get(currentIndex).getTxt();
                    }
                    if(currentIndex == db.size()){
                        if(topicNum == 1){
                            topicIntent.putExtra("INDEX1", 0);
                            topicIntent.putExtra("INDEX2", index2);
                            topicIntent.putExtra("TOPICNUM", 1);
                        }
                        else{
                            topicIntent.putExtra("INDEX1", index1);
                            topicIntent.putExtra("INDEX2", 0);
                            topicIntent.putExtra("TOPICNUM", 2);
                        }
                        topicIntent.putParcelableArrayListExtra("db", db);
                        startActivity(topicIntent);
                    }
                }
                //waiting for the audio button to be pressed
                else if(v.getId() == R.id.audioButton){
                    //Log.i(TAG, "audio button pressed");

                    if (!isPause) {
                        findViewById(R.id.audioButton).setVisibility(View.INVISIBLE);
                        findViewById(R.id.pauseButton).setVisibility(View.VISIBLE);
                        String filename = db.get(currentIndex).getFile();
                            //loadFile(filename);
                        link = path +"/Topic1Audio/" + filename;
                            //Uri linku = Uri.parse(link);
                        try {
                            player.setDataSource(link);
                            Thread a = new Thread() {
                                public void run() {
                                    try {
                                        player.prepare();
                                        player.start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            a.start();
                        }catch(IOException e){
                            e.printStackTrace();
                        }

                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                    }
                    else{
                        isPause = false;
                        player.setScreenOnWhilePlaying(true);
                        player.setWakeMode(Demo.this, PowerManager.PARTIAL_WAKE_LOCK);
                        player.start();
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        findViewById(R.id.audioButton).setVisibility(View.INVISIBLE);
                        findViewById(R.id.pauseButton).setVisibility(View.VISIBLE);
                    }
                }
                else if(v.getId() == R.id.recordButton){
      //              Log.i(TAG, "recording button pressed");
                    //startRecording();
                    findViewById(R.id.recordButton).setVisibility(View.GONE);
                    findViewById(R.id.audioButton).setVisibility(View.GONE);
                    findViewById(R.id.stopButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.pauseButton).setVisibility(View.GONE);
                }
                else if(v.getId() == R.id.stopButton){
        //            Log.i(TAG, "stop button is pressed");
                    //stopRecording();
                    findViewById(R.id.recordButton).setVisibility(View.INVISIBLE);
                    findViewById(R.id.stopButton).setVisibility(View.INVISIBLE);
                    currentIndex++;
          //          Log.i("record stopped", "stopped index: " + currentIndex);
                    findViewById(R.id.recordButton).setVisibility(View.GONE);


                }
                else if(v.getId() == R.id.pauseButton){
            //        Log.i(TAG, "Pause button is pressed");
                    player.pause();
                    isPause = true;
                    findViewById(R.id.pauseButton).setVisibility(View.INVISIBLE);
                    findViewById(R.id.audioButton).setVisibility(View.VISIBLE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                else if(v.getId() == R.id.backToTopic){
              //      Log.i(TAG, "Back to Topic button is pressed");
                    if(topicNum == 1){
                        topicIntent.putExtra("INDEX1", currentIndex);
                        topicIntent.putExtra("INDEX2", index2);
                        topicIntent.putExtra("TOPICNUM", 1);
                    }
                    else{
                        topicIntent.putExtra("INDEX1", index1);
                        topicIntent.putExtra("INDEX2", currentIndex);
                        topicIntent.putExtra("TOPICNUM", 2);
                    }
                    topicIntent.putParcelableArrayListExtra("db", db);
                    startActivity(topicIntent);
                }
            }

        };
        //setting the button listeners to the handler
        findViewById(R.id.nextButton).setOnClickListener(handler);
        findViewById(R.id.audioButton).setOnClickListener(handler);
        findViewById(R.id.recordButton).setOnClickListener(handler);
        findViewById(R.id.stopButton).setOnClickListener(handler);
        findViewById(R.id.pauseButton).setOnClickListener(handler);
        findViewById(R.id.backToTopic).setOnClickListener(handler);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                if(db.get(currentIndex).getSubType().equals("Listen")) {
                    /*if (db.get(currentIndex + 1).getType().equals("Q")) {
                //        Log.i("Audio CompletionListener", "next message is a quiz!");
                        if(topicNum == 1){
                            intent.putExtra("INDEX1", currentIndex + 1);
                            intent.putExtra("INDEX2", index2);
                            intent.putExtra("TOPICNUM", 1);
                        }
                        else{
                            intent.putExtra("INDEX1", index1);
                            intent.putExtra("INDEX2", currentIndex + 1);
                            intent.putExtra("TOPICNUM", 2);
                        }
                        startActivity(intent);
                        player.reset();
                    } else {*/
                        currentIndex++;
                        findViewById(R.id.nextButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.pauseButton).setVisibility(View.INVISIBLE);
                        player.reset();

                    //}
                }
                else if(db.get(currentIndex).getSubType().equals("Record")){
                    findViewById(R.id.recordButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.pauseButton).setVisibility(View.GONE);
                    player.reset();
                }

            }
        });
    }
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.stop();
        mp.release();
        mp=null;
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
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
    @Override
    protected void onPause() {
        //Log.i("Demo Activity", "OnPause");
        super.onPause();
        player.release();
        player = null;
        /*SharedPreferences.Editor ed = mPrefs.edit();
        if(topicNum == 1){
            ed.putInt("index1", currentIndex);
            ed.putInt("index2", index2);
        }
        else {
            ed.putInt("index1", index1);
            ed.putInt("index2", currentIndex);
        }
        ed.putInt("topicNum", topicNum);
        ed.commit();
*/

    }
    @Override
    protected void onStop() {
        super.onStop();
        //player.release();
    }
    @Override
    protected void onDestroy() {
        //Log.i("Demo Activity", "OnDestroy");
        super.onDestroy();
        player = null;
        /*SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("index1", 0);
        ed.putInt("index2", 0);
        ed.commit();*/
    }
}






