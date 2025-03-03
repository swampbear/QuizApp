package com.swampbeardev.quizapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

    private final LiveData<List<GalleryItem>> galleryItems;


    private final GalleryItemRepository repository;

    public GalleryViewModel(@NonNull Application application) {
        super(application);

        // Initialize LiveData from the application's current list
        repository = new GalleryItemRepository(application);
        galleryItems = (repository.getAllGalleryItems());
    }

    public LiveData<List<GalleryItem>> getGalleryItems() {
        return galleryItems;
    }

    public void removeGalleryItem(GalleryItem item) {
        repository.delete(item);
    }

    public void sortEntriesAZ() {
        //Transformations.map(galleryItems, galleryItems1 -> galleryItems1.sort(Comparator.comparing(GalleryItem::getImageName).thenComparing(GalleryItem::getImageName)));
    }

    public void sortEntriesZA() {
      /**  List<GalleryItem> items = new ArrayList<>(app.getGalleryItems());
        Collections.sort(items, Comparator.comparing(GalleryItem::getImageName).reversed());
        app.setGalleryItems(new ArrayList<>(items));
        galleryItems.setValue(items);**/
    }

    public void insert(GalleryItem newItem) {
        repository.insert(newItem);
    }
}

