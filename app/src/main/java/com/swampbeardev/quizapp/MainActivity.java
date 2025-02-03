package com.swampbeardev.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        QuizApplication app = (QuizApplication) getApplication();
        ArrayList<Uri> imageUris = new ArrayList<>();
        imageUris.add(Uri.parse("android.resource://com.swampbeardev.quizapp/" + R.drawable.jobjorn));

        if (app.getGalleryItems() == null) {
            ArrayList<GalleryItem> galleryItems = new ArrayList<>();
            // Sample data
            galleryItems.add(new GalleryItem(imageUris.get(0), "Image 1"));
            app.setGalleryItems(galleryItems);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.quizButton) {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.galleryButton) {
            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(intent);
        }
    }
}