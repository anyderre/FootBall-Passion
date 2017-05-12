package com.example.android.footballpassion;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.button;


public class MainActivity extends AppCompatActivity{


    private TextView textView ;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // android.support.v7.app.ActionBar ab =getSupportActionBar();
        //ab.setTitle("FootAndBall");
        //ab.setIcon(R.mipmap.logo);
        //getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Live");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_videocam_black_24dp));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("News");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_chat_black_24dp));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Discussion");
        spec.setContent(R.id.tab3);
        spec.setIndicator("", ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_forum_black_24dp));
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        textView= (TextView) findViewById(R.id.responseView);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        final CatchingData catchingData = new CatchingData(getApplicationContext(), textView, progressBar);

        Button button = (Button)findViewById(R.id.queryButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (catchingData.isConnected(getApplicationContext())){
                    catchingData.execute("http://api.football-data.org/v1/competions/?season=2017");
                }else{
                    textView.setText("Verify your network connection");
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;

        Toast toast;
        switch (item.getItemId()) {
            case R.id.about:
                //startActivity(new Intent(this, About));
                text = "About";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            case R.id.help:
                text = "Help";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            case R.id.app_bar_search:
                text = "Search";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            case R.id.app_bar_user:
                text = "User";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;

        }

        return true;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());


    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
