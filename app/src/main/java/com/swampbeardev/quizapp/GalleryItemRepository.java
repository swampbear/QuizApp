package com.swampbeardev.quizapp;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class GalleryItemRepository {

    private final GalleryItemDAO galleryItemDAO;
    private final LiveData<List<GalleryItem>> galleryItems;
    public GalleryItemRepository(Application application){
        AppDatabase appDatabase;
        appDatabase = AppDatabase.getDatabase(application);
        galleryItemDAO = appDatabase.galleryItemDAO();
        galleryItems = galleryItemDAO.getAll();
    }

    public LiveData<List<GalleryItem>> getAllGalleryItems(){
        return galleryItems;
    }

    public void delete(GalleryItem item){
        AppDatabase.databaseWriteExecutor.execute(() -> galleryItemDAO.delete(item));
    }

    public void insert(GalleryItem item){
        AppDatabase.databaseWriteExecutor.execute(() -> galleryItemDAO.insertAll(item));
    }


}
