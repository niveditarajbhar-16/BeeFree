package com.sushant.contactapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Vector;

public class SelfDefenceVideoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Vector<Youtube_video> youtubeVideos = new Vector<Youtube_video>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_defence_video);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        youtubeVideos.add( new Youtube_video("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/bR476k1hPNk\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new Youtube_video("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/T7aNSRoDCmg&list=RDCMUC4rlAVgAK0SGk-yTfe48Qpw&start_radio=1&t=2\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new Youtube_video("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/jkvm13QYfpI\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new Youtube_video("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/M4_8PoRQP8w\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new Youtube_video("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/KVpxP3ZZtAc\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);
    }
}
