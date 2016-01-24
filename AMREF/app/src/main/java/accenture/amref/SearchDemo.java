package accenture.amref;
/**
 * Author: Charles Zhang
 * date: 3/20/15
 * Purpose: Demo of Search
 */
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.Handler;



public class SearchDemo extends ActionBarActivity {
    private TextView t;
    private Thread thread;
    final Intent receiveIntent = getIntent();
    Handler hand = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_demo);
        t = (TextView)findViewById(R.id.searchTxt);
        t.setMovementMethod(new ScrollingMovementMethod());
        t.append("Search Topic 1" + "\n");
        t.append("\n" + "Reply with your choice" + "\n" + "1-Importance & Types of FP" + "\n" +
                "2-Myths and Misconceptions on FP" + "\n" + "3-Topic Summary \n" + "4-Topic Quiz \n");
        hand.postDelayed(run, 2000);
        hand.postDelayed(run2, 3000);
    }
    Runnable run = new Runnable(){
        @Override
        public void run(){
            t.append("\n 3 \n");

        }
    };
    Runnable run2 = new Runnable(){
        @Override
        public void run(){
            t.append("\n 1/4 Hello, you will now receive SMSs to remind you key points on Family Planning"
                    + "\n \n 2/4 There are permanent, hormonal , barrier, and natural methods of family planning. Intrauterine devices also known as IUDs or coil can also be used."
                    + "\n \n 3/4 Always refer the community members of reproductive age who require more information on family planning to the health facility"
                    + "\n \n 4/4 Remember that family planning methods can be used by both men and women"
                    + "\n \n You have completed your search Topic, and will now return to the Topic of the week");
        }


    };
    @Override
    protected void onResume() {
        super.onResume();



            }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_demo, menu);
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
