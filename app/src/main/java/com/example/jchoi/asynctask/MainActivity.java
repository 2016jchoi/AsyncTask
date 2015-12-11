package com.example.jchoi.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    public ProgressBar bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button = (Button) findViewById(R.id.progress);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DownloadFilesTask dsk = new DownloadFilesTask();
                dsk.execute("http://shakespeare.mit.edu/hamlet/full.html");
            }
        });

        bar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... n) {
            StringBuilder sb = new StringBuilder();

            try {
                URL hamlet = new URL(n[0]);
                URLConnection connection = hamlet.openConnection();
                connection.connect();
//                int fileLength = connection.getContentLength();
//                Log.i("The length of the file is ", fileLength+".");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine+"\n");
                    Log.i("Line ", inputLine);
//                    total = sb.length();
//                    if (fileLength > 0)
//                        publishProgress((int)((total*100)/fileLength));
                }
                in.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return sb.toString();
        }
        protected void onProgressUpdate(Integer... progress) {
           // bar.setProgress(progress[0]);
        }
        protected void onPostExecute(String sb) {
            Context context = getApplicationContext();
            CharSequence text = "Download Complete!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            //try{
             //   File f = new File("/users/2016jchoi/output.txt");
            //    FileWriter fw = new FileWriter(f);
            //    BufferedWriter bw = new BufferedWriter(fw);
            //    bw.write(sb);
            //    bw.close();
           // }
            //catch (IOException iox) {
            //    iox.printStackTrace();
            //}
            //Log.i("The document is: ", sb);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

