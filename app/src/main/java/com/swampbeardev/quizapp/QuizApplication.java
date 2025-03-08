package com.swampbeardev.quizapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Application class for the quiz app.
 * Keeps track of the gallery items across activities.
 */
public class QuizApplication extends Application {
    private ArrayList<GalleryItem> galleryItems = new ArrayList<>();

    public ArrayList<GalleryItem> getGalleryItems() {
        return galleryItems;
    }

    public void addGalleryItem(GalleryItem item) {
        galleryItems.add(item);
    }

    public void removeGalleryItem(GalleryItem item) {
        galleryItems.remove(item);
    }

    public void setGalleryItems(ArrayList<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }
}
