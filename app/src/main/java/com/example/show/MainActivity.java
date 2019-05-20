package com.example.show;

import android.Manifest;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.show.chapters.ChapterAdapter;
import com.example.show.chapters.ChapterFunction;
import com.example.show.mediaplayer.MusicPlayer;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public final static int LOGIN_REQUEST = 101;
    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth auth;
    private ImageView btPlay;
    private ImageView btRewind;
    private ImageView btFastForward;
    private TextView txStartTime;
    private TextView txEndTime;
    private SeekBar seekBar;
    private RecyclerView chapterRecycler;
    private LinearLayout maskLayer;

    MediaPlayer player = new MediaPlayer();
    private MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);

        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginPage = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(loginPage, LOGIN_REQUEST);
                }
            }
        });

        //findViewById
        findViewById();

        //create mediaPlayer
        musicPlayer = new MusicPlayer(MainActivity.this,player, seekBar, txEndTime, txStartTime, btPlay, btFastForward, btRewind);
        musicPlayer.setMusic(R.raw.the_other);
    }

    private void findViewById() {
        btPlay = findViewById(R.id.imgPlay);
        btRewind = findViewById(R.id.imgRewind);
        btFastForward = findViewById(R.id.imgFastForward);

        txStartTime = findViewById(R.id.txStartTime);
        txEndTime = findViewById(R.id.txEndTime);

        seekBar = findViewById(R.id.sb_playtime);

        maskLayer = findViewById(R.id.maskLayer);
        maskLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                chapterRecycler.setVisibility(View.GONE);
            }
        });
        chapterRecycler = findViewById(R.id.recyclerChapters);
        ChapterFunction chapterFunction = new ChapterFunction(this, chapterRecycler, auth.getUid());
    }


    //-----------------use intent and get result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case LOGIN_REQUEST:
                if (resultCode!=RESULT_OK) finish();
                break;
        }
    }

//------------------ menu botton
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toorber_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                auth.signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.bt_speed:
                    return true;
                case R.id.bt_chapters:
                    if (chapterRecycler.getVisibility() == View.GONE){
                        maskLayer.setVisibility(View.VISIBLE);
                        chapterRecycler.setVisibility(View.VISIBLE);
                    }else {
                        maskLayer.setVisibility(View.GONE);
                        chapterRecycler.setVisibility(View.GONE);
                    }

                    return true;
                case R.id.bt_sleepTimer:
                    return true;
                case R.id.bt_bookmark:
                    return true;
            }
            return false;
        }
    };
}
