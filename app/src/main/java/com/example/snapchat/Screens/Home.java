package com.example.snapchat.Screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Matrix;

import com.example.snapchat.Entities.AccountUser;
import com.example.snapchat.FirebaseRef.FirebaseDatabaseRef;
import com.example.snapchat.R;

import com.example.snapchat.Store.UserStore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Home extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 10;

    // This is an array of all the permission specified in the manifest.
    private String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};

    TextView txtUserName;
    Button btnSnap;
    Button btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtUserName = findViewById(R.id.textView11);
        txtUserName.setText(UserStore.getSignedInUser().getEmail());

        btnSnap = findViewById(R.id.btnCapture);
        btnChat = findViewById(R.id.btnChat);

        viewFinder = findViewById(R.id.view_finder);

        FirebaseDatabaseRef.getUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AccountUser user = snapshot.getValue(AccountUser.class);
                    UserStore.getAllUsers().add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        if (allPermissionsGranted()) {
            viewFinder.post(() -> {
                startCamera();
            });
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        viewFinder.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                updateTransform();
            }
        });

    }

    private ExecutorService excecutor = Executors.newSingleThreadExecutor();
    private TextureView viewFinder;

    private void startCamera() {
        // Create configuration object for the viewfinder use case
        PreviewConfig.Builder builder = new PreviewConfig.Builder();
        builder.setTargetResolution(new Size(viewFinder.getWidth(), viewFinder.getHeight()));
        PreviewConfig config = builder.build();

        // Build the viewfinder use case
        Preview preview = new Preview(config);

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(@NonNull Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) viewFinder.getParent();
                parent.removeView(viewFinder);
                parent.addView(viewFinder, 0);

                viewFinder.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });

// Create configuration object for the image capture use case
        ImageCaptureConfig.Builder imgCaptureBuilder = new ImageCaptureConfig.Builder();
        imgCaptureBuilder.setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY);

        ImageCaptureConfig imageCaptureConfig = imgCaptureBuilder.build();

        // Build the image capture use case and attach button click listener
        ImageCapture imageCapture = new ImageCapture(imageCaptureConfig);
        btnSnap.setOnClickListener(new View.OnClickListener() {
            File[] files = getExternalMediaDirs();
            File file = new File(files[0], "${System.currentTimeMillis()}.jpg");



            @Override
            public void onClick(View v) {
                imageCapture.takePicture(file, excecutor, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String filePath = file.getPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);


                        Toast.makeText(getApplicationContext(), "Capture success!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                        Toast.makeText(getApplicationContext(), "Capture failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        // Bind use cases to lifecycle
        CameraX.bindToLifecycle(this, preview);
    }

    private void updateTransform() {
        Matrix matrix = new Matrix();

        // Compute the center of the view finder
        float centerX = viewFinder.getWidth() / 2f;
        float centerY = viewFinder.getHeight() / 2f;

        // Correct preview output to account for display rotation

        float rotationDegrees = 0;
        switch (viewFinder.getDisplay().getRotation()) {
            case Surface.ROTATION_0:
                rotationDegrees = 0;
                break;
            case Surface.ROTATION_90:
                rotationDegrees = 90;
                break;
            case Surface.ROTATION_180:
                rotationDegrees = 180;
                break;
            case Surface.ROTATION_270:
                rotationDegrees = 270;
                break;
            default:
                matrix.postRotate(-rotationDegrees, centerX, centerY);
                ;
        }
        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix);
    }

    private void storePhotoToStorage(Bitmap cbmp, String pathFileName) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post(() -> {
                    startCamera();
                });
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private boolean allPermissionsGranted() {
        return ContextCompat.checkSelfPermission(getBaseContext(), REQUIRED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }

}
