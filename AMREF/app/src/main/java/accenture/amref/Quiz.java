package accenture.amref;
/**
 * Author: Charles Zhang
 * date: 1/14/15
 * Purpose: Activity for Quizzes
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Quiz extends Activity {
    private TextView tv;
    private boolean a1, a2, a3, a4, a5;
    private ArrayList<Message> db;
    private SharedPreferences mPrefs;
    private int index, index1, index2, topicNum, leaveIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mPrefs = getSharedPreferences("index",0);
        findViewById(R.id.ans1Button).setVisibility(View.GONE);
        findViewById(R.id.ans2Button).setVisibility(View.GONE);
        findViewById(R.id.ans3Button).setVisibility(View.GONE);
        findViewById(R.id.ans4Button).setVisibility(View.GONE);
        findViewById(R.id.ans5Button).setVisibility(View.GONE);
        findViewById(R.id.textResponse).setVisibility(View.GONE);
        //setting up the intents
        final Intent intent1 = new Intent(this, Demo.class);
        final Intent currentIntent = getIntent();
        index1 = currentIntent.getIntExtra("INDEX1", 0);
        index2 = currentIntent.getIntExtra("INDEX2", 0);
        topicNum = currentIntent.getIntExtra("TOPICNUM", 0);
        if(topicNum == 1){
            index = index1;
        }
        else{
            index = index2;
        }

        final Intent topicIntent = new Intent(this, Topic.class);

        if(topicNum == 1){
            leaveIndex = mPrefs.getInt("index1",index2);
        }
        else{
            leaveIndex = mPrefs.getInt("index2",index2);
        }
        //initialize db
        db = new ArrayList<Message>();
        db = currentIntent.getParcelableArrayListExtra("db");

        if(leaveIndex > index && leaveIndex!= db.size()){
            index = leaveIndex;
        }
        //initialize toast message
        Context context = getApplicationContext();
        CharSequence text = "Test";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration);

        //Get index of answers in the message
        final int indexa1 = db.get(index).getTxt().indexOf("1.", 0);
        final int indexa2 = db.get(index).getTxt().indexOf("2.", 0);
        final int indexa3 = db.get(index).getTxt().indexOf("3.", 0);

         //initialize text view to put the question
        tv = (TextView) findViewById(R.id.questionText);
        tv.setText(db.get(index).getTxt());

        //initialize a's to false
        a1 = false;
        a2 = false;
        a3 = false;
        a4 = false;
        a5 = false;
        //If this is a multiple choice question(subtype of mc)
        if(db.get(index).getSubType().equals("MC")) {
            //set buttons 4, 5, and text response to not be visible
            findViewById(R.id.ans1Button).setVisibility(View.VISIBLE);
            findViewById(R.id.ans2Button).setVisibility(View.VISIBLE);
            findViewById(R.id.ans3Button).setVisibility(View.VISIBLE);
            findViewById(R.id.ans4Button).setVisibility(View.GONE);
            findViewById(R.id.ans5Button).setVisibility(View.GONE);
            findViewById(R.id.textResponse).setVisibility(View.GONE);

            View.OnClickListener handler = new View.OnClickListener() {
                public void onClick(View v) {

                    switch (v.getId()) {

                        case R.id.ans1Button: //new topic will be shown
                            a1 = true;
                            //setting toast message depending on the correct answer
                            if (db.get(index).getAnswer().equals("1")) {
                                toast.setText("Correct, " + db.get(index).getTxt().substring(indexa1, indexa2 - 1));
                            } else if (db.get(index).getAnswer().equals("2")) {
                                toast.setText("Incorrect, " + db.get(index).getTxt().substring(indexa2, indexa3 - 1));
                            } else if (db.get(index).getAnswer().equals("3")) {
                                toast.setText("Incorrect, " + db.get(index).getTxt().substring(indexa3, db.get(index).getTxt().length()));
                            }
                            else{
                                break;
                            }
                            //display toast
                            toast.show();
                            break;
                        case R.id.ans2Button:
                            a2=true;
                            //setting toast message depending on the correct answer
                            if (db.get(index).getAnswer().equals("1")) {
                                toast.setText("Incorrect, " + db.get(index).getTxt().substring(indexa1, indexa2 - 1));
                            } else if (db.get(index).getAnswer().equals("2")) {
                                toast.setText("Correct, " + db.get(index).getTxt().substring(indexa2, indexa3 - 1));
                            } else if (db.get(index).getAnswer().equals("3")) {
                                toast.setText("Incorrect, " + db.get(index).getTxt().substring(indexa3, db.get(index).getTxt().length()));
                            }
                            else{
                                break;
                            }
                            //display toast
                            toast.show();
                            break;
                        case R.id.ans3Button: //new activity
                            a3 = true;
                            //setting toast message depending on the correct answer
                            if (db.get(index).getAnswer().equals("1")) {
                                toast.setText("Incorrect, " + db.get(index).getTxt().substring(indexa1, indexa2 - 1));
                            } else if (db.get(index).getAnswer().equals("2")) {
                                toast.setText("Incorrect, " + db.get(index).getTxt().substring(indexa2, indexa3 - 1));
                            } else if (db.get(index).getAnswer().equals("3")) {
                                toast.setText("Correct, " + db.get(index).getTxt().substring(indexa3, db.get(index).getTxt().length()));
                            }
                            else{
                                break;
                            }
                            //display toast
                            toast.show();
                            break;

                        case R.id.nextButton: //new activity
                            //Create Intent for new activity before the listening and  start activity demo
                            //setting toast message depending on the correct answer
                            if ((a1 || a2 || a3) == false) {
                                toast.setText("Please select an answer!");
                                //display toast
                                toast.show();
                                break;
                            }
                            else{
                                a1=false;
                                a2=false;
                                a3=false;
                                if(db.get(index + 1).getType().equals("Q")){
                                    if(topicNum == 1){
                                        currentIntent.putExtra("INDEX1", index + 1);
                                        currentIntent.putExtra("INDEX2", index2);
                                        currentIntent.putExtra("TOPICNUM", 1);
                                    }
                                    else{
                                        currentIntent.putExtra("INDEX1", index1);
                                        currentIntent.putExtra("INDEX2", index + 1);
                                        currentIntent.putExtra("TOPICNUM", 2);
                                    }
                                    currentIntent.putParcelableArrayListExtra("db", db);
                                    currentIntent.putExtra("Context",getIntent().getStringExtra("Context"));
                                    startActivity(currentIntent);
                                }
                                else{
                                    //put index in extra
                                    if(topicNum == 1){
                                        intent1.putExtra("INDEX1", index + 1);
                                        intent1.putExtra("INDEX2", index2);
                                        intent1.putExtra("TOPICNUM", 1);
                                    }
                                    else{
                                        intent1.putExtra("INDEX1", index1);
                                        intent1.putExtra("INDEX2", index + 1);
                                        intent1.putExtra("TOPICNUM", 2);
                                    }
                                    intent1.putParcelableArrayListExtra("db", db);
                                    intent1.putExtra("Context", getIntent().getStringExtra("Context"));
                                    startActivity(intent1);
                                }

                            }
                            break;
                        case R.id.backToTopic:
                            if(topicNum == 1){
                                topicIntent.putExtra("INDEX1", index);
                                topicIntent.putExtra("INDEX2", index2);
                                topicIntent.putExtra("TOPICNUM", 1);
                            }
                            else{
                                topicIntent.putExtra("INDEX1", index1);
                                topicIntent.putExtra("INDEX2", index);
                                topicIntent.putExtra("TOPICNUM", 2);
                            }
                            topicIntent.putParcelableArrayListExtra("db", db);
                            startActivity(topicIntent);
                        break;
                    }
                }
            };
            findViewById(R.id.ans1Button).setOnClickListener(handler);
            findViewById(R.id.ans2Button).setOnClickListener(handler);
            findViewById(R.id.ans3Button).setOnClickListener(handler);
            findViewById(R.id.ans4Button).setOnClickListener(handler);
            findViewById(R.id.ans5Button).setOnClickListener(handler);
            findViewById(R.id.textResponse).setOnClickListener(handler);
            findViewById(R.id.nextButton).setOnClickListener(handler);
            findViewById(R.id.backToTopic).setOnClickListener(handler);
        }

        //Case for Rating question
        else if(db.get(index).getSubType().equals("Rate")) {
            findViewById(R.id.ans1Button).setVisibility(View.VISIBLE);
            findViewById(R.id.ans2Button).setVisibility(View.VISIBLE);
            findViewById(R.id.ans3Button).setVisibility(View.VISIBLE);
            findViewById(R.id.ans4Button).setVisibility(View.VISIBLE);
            findViewById(R.id.ans5Button).setVisibility(View.VISIBLE);
            findViewById(R.id.textResponse).setVisibility(View.GONE);

            View.OnClickListener handler1 = new View.OnClickListener() {
                public void onClick(View v) {
                    //we will use switch statement and get the button's id to make things easier
                    switch (v.getId()) {
                        case R.id.ans1Button:
                            a1= true;
                            db.get(index).setAnswer("1");
                            toast.setText("You have selected 1");
                            toast.show();
                            break;
                        case R.id.ans2Button:
                            a2=true;
                            db.get(index).setAnswer("2");
                            toast.setText("You have selected 2");
                            toast.show();
                            break;
                        case R.id.ans3Button:
                            a3=true;
                            db.get(index).setAnswer("3");
                            toast.setText("You have selected 3");
                            toast.show();
                            break;
                        case R.id.ans4Button:
                            a4=true;
                            db.get(index).setAnswer("4");
                            toast.setText("You have selected 4");
                            toast.show();
                            break;
                        case R.id.ans5Button:
                            a5=true;
                            db.get(index).setAnswer("5");
                            toast.setText("You have selected 5");
                            toast.show();
                            break;
                        case R.id.nextButton:
                            if (a1||a2||a3||a4||a5) {
                                //Log.i("Quiz", db.get(index).getAnswer());
                                a1 = false;
                                a2 = false;
                                a3 = false;
                                a4 = false;
                                a5 = false;
                                if (db.get(index + 1).getType().equals("Q")) {
                                    if(topicNum == 1){
                                        currentIntent.putExtra("INDEX1", index + 1);
                                        currentIntent.putExtra("INDEX2", index2);
                                        currentIntent.putExtra("TOPICNUM", 1);
                                    }
                                    else{
                                        currentIntent.putExtra("INDEX1", index1);
                                        currentIntent.putExtra("INDEX2", index + 1);
                                        currentIntent.putExtra("TOPICNUM", 2);
                                    }
                                    currentIntent.putParcelableArrayListExtra("db", db);
                                    currentIntent.putExtra("Context", getIntent().getStringExtra("Context"));
                                    startActivity(currentIntent);
                                } else {
                                    //put index in extra
                                    if(topicNum == 1){
                                        intent1.putExtra("INDEX1", index + 1);
                                        intent1.putExtra("INDEX2", index2);
                                        intent1.putExtra("TOPICNUM", 1);
                                    }
                                    else{
                                        intent1.putExtra("INDEX1", index1);
                                        intent1.putExtra("INDEX2", index + 1);
                                        intent1.putExtra("TOPICNUM", 2);
                                    }
                                    intent1.putParcelableArrayListExtra("db", db);
                                    intent1.putExtra("Context", getIntent().getStringExtra("Context"));
                                    startActivity(intent1);
                                }
                                break;
                            } else {
                                toast.setText("Please select an answer!");
                                toast.show();
                                break;
                            }
                        case R.id.backToTopic:

                            //Log.i("Quiz", "Back to Topic button is pressed");
                            if(topicNum == 1){
                                topicIntent.putExtra("INDEX1", index);
                                topicIntent.putExtra("INDEX2", index2);
                                topicIntent.putExtra("TOPICNUM", 1);
                            }
                            else{
                                topicIntent.putExtra("INDEX1", index1);
                                topicIntent.putExtra("INDEX2", index);
                                topicIntent.putExtra("TOPICNUM", 2);
                            }
                            topicIntent.putParcelableArrayListExtra("db", db);
                            startActivity(topicIntent);
                            break;

                    }
                }
            };
            findViewById(R.id.ans1Button).setOnClickListener(handler1);
            findViewById(R.id.ans2Button).setOnClickListener(handler1);
            findViewById(R.id.ans3Button).setOnClickListener(handler1);
            findViewById(R.id.ans4Button).setOnClickListener(handler1);
            findViewById(R.id.ans5Button).setOnClickListener(handler1);
            findViewById(R.id.textResponse).setOnClickListener(handler1);
            findViewById(R.id.nextButton).setOnClickListener(handler1);
            findViewById(R.id.backToTopic).setOnClickListener(handler1);
        }
                    //Case for Response question
        else if(db.get(index).getSubType().equals("Response")){
            findViewById(R.id.ans1Button).setVisibility(View.GONE);
            findViewById(R.id.ans2Button).setVisibility(View.GONE);
            findViewById(R.id.ans3Button).setVisibility(View.GONE);
            findViewById(R.id.ans4Button).setVisibility(View.GONE);
            findViewById(R.id.ans5Button).setVisibility(View.GONE);
            findViewById(R.id.textResponse).setVisibility(View.VISIBLE);

            View.OnClickListener handler2 = new View.OnClickListener() {
                public void onClick(View v) {
                    switch(v.getId()){
                        case R.id.nextButton:
                            EditText editText = (EditText) findViewById(R.id.textResponse);
                            db.get(index).setAnswer(editText.getText().toString());
                            if(!db.get(index).getAnswer().equals("")) {
                                if (db.get(index + 1).getType().equals("Q")) {
                                    if(topicNum == 1){
                                        currentIntent.putExtra("INDEX1", index + 1);
                                        currentIntent.putExtra("INDEX2", index2);
                                        currentIntent.putExtra("TOPICNUM", 1);
                                    }
                                    else{
                                        currentIntent.putExtra("INDEX1", index1);
                                        currentIntent.putExtra("INDEX2", index + 1);
                                        currentIntent.putExtra("TOPICNUM", 2);
                                    }
                                    currentIntent.putParcelableArrayListExtra("db", db);
                                    currentIntent.putExtra("Context", getIntent().getStringExtra("Context"));
                                    startActivity(currentIntent);
                                    break;
                                } else {
                                //put index in extra
                                    if(topicNum == 1){
                                        intent1.putExtra("INDEX1", index + 1);
                                        intent1.putExtra("INDEX2", index2);
                                        intent1.putExtra("TOPICNUM", 1);
                                    }
                                    else{
                                        intent1.putExtra("INDEX1", index1);
                                        intent1.putExtra("INDEX2", index + 1);
                                        intent1.putExtra("TOPICNUM", 2);
                                    }
                                    intent1.putParcelableArrayListExtra("db",db);
                                    intent1.putExtra("Context",getIntent().getStringExtra("Context"));
                                    startActivity(intent1);
                                    break;
                                }
                            }
                            else{
                                break;
                            }
                        case R.id.backToTopic:

                            //Log.i("Quiz", "Back to Topic button is pressed");
                            if(topicNum == 1){
                                topicIntent.putExtra("INDEX1", index);
                                topicIntent.putExtra("INDEX2", index2);
                                topicIntent.putExtra("TOPICNUM", 1);
                            }
                            else{
                                topicIntent.putExtra("INDEX1", index1);
                                topicIntent.putExtra("INDEX2", index);
                                topicIntent.putExtra("TOPICNUM", 2);
                            }
                            topicIntent.putParcelableArrayListExtra("db", db);
                            startActivity(topicIntent);
                            break;


                    }
                    }
            };
            findViewById(R.id.ans1Button).setOnClickListener(handler2);
            findViewById(R.id.ans2Button).setOnClickListener(handler2);
            findViewById(R.id.ans3Button).setOnClickListener(handler2);
            findViewById(R.id.ans4Button).setOnClickListener(handler2);
            findViewById(R.id.ans5Button).setOnClickListener(handler2);
            findViewById(R.id.textResponse).setOnClickListener(handler2);
            findViewById(R.id.nextButton).setOnClickListener(handler2);
            findViewById(R.id.backToTopic).setOnClickListener(handler2);
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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
        SharedPreferences.Editor ed = mPrefs.edit();
        if(topicNum == 1){
            ed.putInt("INDEX1", index);
            ed.putInt("INDEX2", index2);
            ed.putInt("TOPICNUM", 1);
        }
        else{
            ed.putInt("INDEX1", index1);
            ed.putInt("INDEX2", index);
            ed.putInt("TOPICNUM", 2);
        }
        ed.commit();
    }
}
