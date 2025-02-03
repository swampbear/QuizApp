package com.swampbeardev.quizapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Activity for the gallery screen.
 */
public class GalleryActivity extends AppCompatActivity {

    private GalleryAdapter adapter;
    private QuizApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        app = (QuizApplication) getApplication();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GalleryAdapter(app.getGalleryItems());
        adapter.setOnClickListener(position -> {
            GalleryItem item = app.getGalleryItems().get(position);
            app.removeGalleryItem(item);
            adapter.notifyItemRemoved(position);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

    }

    /**
     * Handles click events on the screen.
     * @param view The view that was clicked.
     */
    public void onClick(View view) {
        if (view.getId() == R.id.addEntryButton) {
            AddEntryBottomSheet bottomSheet = new AddEntryBottomSheet(adapter);
            bottomSheet.show(getSupportFragmentManager(), "AddEntryBottomSheet");
        }
        if(view.getId() == R.id.backButton){
            finish();
        }
        if (view.getId() == R.id.ZAsortButton) {
            adapter.sortEntriesZA();
        }
        if (view.getId() == R.id.AZsortButton) {
            adapter.sortEntriesAZ();
        }


    }
}

