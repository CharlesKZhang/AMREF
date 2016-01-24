/**
 * Author: Charles Zhang
 * Date: 1/14/15
 * Purpose: An Activity to display the About page and links to other websites and activities
 */
package accenture.amref;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class AboutHelp extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_help);
        final Intent partnerIntent = new Intent(this, Partners.class);
        final Intent pedagogyIntent = new Intent(this, Pedagogy.class);
        final Intent newsIntent = new Intent(this, News.class);
        try {
            View.OnClickListener handler = new View.OnClickListener() {
                public void onClick(View v) {
                    //we will use switch statement and just
                    //get the button's id to make things easier
                    switch (v.getId()) {

                        case R.id.helpYoutube: //new topic will be shown
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=r1FlTXvFs-0")));
                            break;
                        case R.id.website:
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://ehealth.amref.org/help/")));
                            break;
                        case R.id.partners: //new activity
                            startActivity(partnerIntent);
                            break;
                        case R.id.pedagody: //new activity
                            startActivity(pedagogyIntent);
                            break;
                        case R.id.news:
                            startActivity(newsIntent);
                            break;
                    }
                }
            };

            //we will set the listeners
            findViewById(R.id.helpYoutube).setOnClickListener(handler);
            findViewById(R.id.website).setOnClickListener(handler);
            findViewById(R.id.partners).setOnClickListener(handler);
            findViewById(R.id.pedagody).setOnClickListener(handler);
            findViewById(R.id.news).setOnClickListener(handler  );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
