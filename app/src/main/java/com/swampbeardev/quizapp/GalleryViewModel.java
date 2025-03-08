package com.swampbeardev.quizapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

    private final GalleryItemRepository repository;
    private final LiveData<List<GalleryItem>> galleryItems;  // Unsorted data from Room
    private final MutableLiveData<List<GalleryItem>> sortedGalleryItems = new MutableLiveData<>();

    public GalleryViewModel(@NonNull Application application) {
        super(application);
        repository = new GalleryItemRepository(application);
        galleryItems = repository.getAllGalleryItems();

        // Whenever the repository's LiveData updates, sort it by default (AZ)
        galleryItems.observeForever(new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> items) {
                if (items != null) {
                    List<GalleryItem> sortedList = new ArrayList<>(items);
                    Collections.sort(sortedList, Comparator.comparing(GalleryItem::getImageName));
                    sortedGalleryItems.setValue(sortedList);
                }
            }
        });
    }

    // Expose the sorted list to the UI
    public LiveData<List<GalleryItem>> getGalleryItems() {
        return sortedGalleryItems;
    }

    // Void function to sort in ascending order (AZ)
    public void sortEntriesAZ() {
        List<GalleryItem> currentList = galleryItems.getValue();
        if (currentList != null) {
            List<GalleryItem> sortedList = new ArrayList<>(currentList);
            Collections.sort(sortedList, Comparator.comparing(GalleryItem::getImageName));
            sortedGalleryItems.setValue(sortedList);
        }
    }

    // Void function to sort in descending order (ZA)
    public void sortEntriesZA() {
        List<GalleryItem> currentList = galleryItems.getValue();
        if (currentList != null) {
            List<GalleryItem> sortedList = new ArrayList<>(currentList);
            Collections.sort(sortedList, Comparator.comparing(GalleryItem::getImageName).reversed());
            sortedGalleryItems.setValue(sortedList);
        }
    }

    public void removeGalleryItem(GalleryItem item) {
        repository.delete(item);
    }

    public void insert(GalleryItem newItem) {
        repository.insert(newItem);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Optionally remove the forever observer to avoid leaks:
        galleryItems.removeObserver(items -> {});
    }
}
