package com.example.snapchat.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snapchat.R;
import com.example.snapchat.Screens.EditImg.EditorMain;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.snapchat.Store.UserStore;

public class Home extends AppCompatActivity implements SurfaceHolder.Callback {
    TextView txtUserName;
    Button btnSnap;
    Button btnChat;
    private SurfaceView surfaceView;
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private Camera.PictureCallback pictureCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtUserName = findViewById(R.id.textView11);

        txtUserName.setText(UserStore.getSignedInUser().getEmail());
        btnSnap = findViewById(R.id.btnCapture);
        btnChat = findViewById(R.id.btnChat);
        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, pictureCallback);
            }
        });

        pictureCallback = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap cbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), null, true);


                String pathFileName = currentDateFormat();
                storePhotoToStorage(cbmp, pathFileName);

                Toast.makeText(getApplicationContext(), "Done!!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), EditorMain.class);
                intent.putExtra("myImage", data);
                startActivity(intent);
                camera.startPreview();
            }
        };

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ChatActivity.class);
                startActivity(intent);
            }
        });

    }

    private void storePhotoToStorage(Bitmap cbmp, String pathFileName) {


    }

    private String currentDateFormat() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTime = dateFormat.format(new Date());
        return currentTime;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
        } catch (Exception ex) {

        }
        Camera.Parameters parameters;
        parameters = camera.getParameters();
        parameters.setPreviewFrameRate(20);
        parameters.setPreviewSize(352, 288);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }



}
