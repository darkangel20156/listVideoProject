package com.example.internet_connection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mstatusConnection;
    private ListView mlistVideo;
    private ListAdapter listAdapter;
    private ArrayList<ListVideo> videoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mstatusConnection = findViewById(R.id.statusConnection);
        mlistVideo = findViewById(R.id.listVideo);

        if (checkInternetConnection()) {
            mstatusConnection.setText("Internet Connection: OK");
        }

        videoArrayList = new ArrayList<>();
        videoArrayList.add(new ListVideo("Christmas song", "https://www.youtube.com/watch?v=1RWXHI4mm6E&ab_channel=PizzaMusic"));
        videoArrayList.add(new ListVideo("Mu vs Liver", "https://www.youtube.com/watch?v=yb_pC8E9DbU&ab_channel=KplusSports"));

        listAdapter = new ListAdapter(MainActivity.this, videoArrayList);
        mlistVideo.setAdapter(listAdapter);

        mlistVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String videoUrl = videoArrayList.get(position).getUrlVideo();
                openYouTubeVideo(videoUrl);
            }
        });
    }

    private void openYouTubeVideo(String videoUrl) {
        try {
            Uri uri = Uri.parse(videoUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.youtube"); // Try to open in the YouTube app first
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // If the YouTube app is not available, open in a web browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            intent.putExtra("force_fullscreen", true);
            startActivity(intent);
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if (connec != null && (
                connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)) {
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this, "Not Connected", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
