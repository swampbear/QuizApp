package com.swampbeardev.quizapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GalleryItemDAO {
    @Query("SELECT * FROM galleryitems")
    LiveData<List<GalleryItem>> getAll();

    @Insert
    void insertAll(GalleryItem... galleryItems);

    @Delete
    void delete(GalleryItem galleryItem);
}
