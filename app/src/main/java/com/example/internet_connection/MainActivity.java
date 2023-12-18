package com.example.internet_connection;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Bitmap bitmap = null;
    Button mbuttonConnect;
    TextView mstatusConnection;

    ListAdapter listAdapter;
    ArrayList<ListVideo> videoArrayList = new ArrayList<>();
    ListVideo listVideo;
    ListView mlistVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbuttonConnect = (Button) findViewById(R.id.buttonConnect);
        mstatusConnection = findViewById(R.id.statusConnection);
        mlistVideo = findViewById(R.id.listVideo);

        mbuttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternetConenction()){
                    mstatusConnection.setText("Internet Connection: OK");
                }
            }
        });

        videoArrayList.add(new ListVideo("Christmas song", "https://www.youtube.com/watch?v=1RWXHI4mm6E&ab_channel=PizzaMusic"));
        videoArrayList.add(new ListVideo("Mu vs Liver", "https://www.youtube.com/watch?v=yb_pC8E9DbU&ab_channel=KplusSports"));

        listAdapter = new ListAdapter(MainActivity.this, videoArrayList);
        mlistVideo.setAdapter(listAdapter);

        String video_path = videoArrayList.get(0).urlVideo;
        Uri uri = Uri.parse(video_path);

        uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private InputStream openHttpConnection(String urlStr) {
        InputStream in = null;
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }


    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}