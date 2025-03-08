
        package com.swampbeardev.quizapp;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private GalleryAdapter adapter;
    private GalleryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
        adapter = new GalleryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Get the ViewModel and observe changes to the gallery items
        viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        viewModel.getGalleryItems().observe(this, galleryItems -> {
            adapter.setGalleryItems(galleryItems);
            adapter.notifyDataSetChanged();
        });

        // Delegate deletion to the ViewModel
        adapter.setOnClickListener(position -> {
            GalleryItem item = adapter.getGalleryItems().get(position);
            viewModel.removeGalleryItem(item);
        });

    }

    /**
     * Handles click events on the screen.
     * @param view The view that was clicked.
     */
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.addEntryButton) {
            // The BottomSheet can now obtain the ViewModel via the Activity if needed.
            AddEntryBottomSheet bottomSheet = new AddEntryBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), "AddEntryBottomSheet");
        } else if (id == R.id.backButton) {
            finish();
        } else if (id == R.id.ZAsortButton) {
            viewModel.sortEntriesZA();
        } else if (id == R.id.AZsortButton) {
            viewModel.sortEntriesAZ();
        }
    }
}




