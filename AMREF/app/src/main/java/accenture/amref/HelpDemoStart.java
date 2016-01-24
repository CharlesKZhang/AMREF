package accenture.amref;
/**
 * Author: Charles Zhang
 * Date: 1/14/15
 * Purpose: Welcome Activity and provides buttons to different activities
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HelpDemoStart extends Activity {
    String TAG = "Welcome Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_demo_start);
        final Intent topicIntent = new Intent(this, Topic.class);
        final Intent aboutIntent = new Intent(this, AboutHelp.class);
        final Intent featIntent = new Intent(this, Features.class);

        findViewById(R.id.buttonDemo).setBackgroundResource(android.R.drawable.btn_default);
        findViewById(R.id.buttonAbout).setBackgroundResource(android.R.drawable.btn_default);
        findViewById(R.id.buttonFeat).setBackgroundResource(android.R.drawable.btn_default);

        try {
            View.OnClickListener handler = new View.OnClickListener() {
                public void onClick(View v) {
                    //we will use switch statement and just
                    //get the button's id to make things easier
                    switch (v.getId()) {

                        case R.id.buttonDemo: //new topic will be shown
                            startActivity(topicIntent);
                            break;
                        case R.id.buttonAbout: //new activity
                            startActivity(aboutIntent);
                            break;
                        case R.id.buttonFeat: //new activity
                            startActivity(featIntent);
                            break;
                    }
                }
            };

            //we will set the listeners
            findViewById(R.id.buttonDemo).setOnClickListener(handler);
            findViewById(R.id.buttonAbout).setOnClickListener(handler);
            findViewById(R.id.buttonFeat).setOnClickListener(handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help_demo_start, menu);
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
