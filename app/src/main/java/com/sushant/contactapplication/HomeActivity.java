package com.sushant.contactapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    CardView addNum, selfdef;

    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    private String mCurrentPhotoPath;
    MediaRecorder mRecorder;
    String mFileName = null;
    private static final String LOG_TAG = "Record_log";
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        addNum = findViewById(R.id.addNumber);
        addNum.setOnClickListener(this);
        selfdef = findViewById(R.id.self_defence);
        selfdef.setOnClickListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(this, "Power Long Press", Toast.LENGTH_SHORT).show();

            String dir = Environment.getExternalStorageState(new File(Environment.DIRECTORY_PICTURES)) + "/picFolder/";
            File newdir = new File(dir);
            newdir.mkdirs();

            String file = dir + "Testing" + ".jpg";
            File newfile = new File(file);
            try {
                newfile.createNewFile();
            } catch (IOException e) {
            }

            //Uri outputFileUri = Uri.fromFile(newfile);
            Uri outputFileUri = null;
            try {
                outputFileUri = FileProvider.getUriForFile(HomeActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

            return true;
        }

        return super.onKeyLongPress(keyCode, event);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        event.startTracking(); // Needed to track long presses
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.share:
                break;

            case R.id.about:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.addNumber:
                Intent i1 = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i1);
                break;

            case R.id.self_defence:
                Intent i2 = new Intent(HomeActivity.this, SelfDefenceVideoActivity.class);
                startActivity(i2);
                break;


            default:
                break;

        }
    }
}
