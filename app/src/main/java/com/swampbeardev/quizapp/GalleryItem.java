package com.swampbeardev.quizapp;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class representing a gallery item.
 * Containing the image URI and the image name.
 */
@Entity(tableName = "galleryitems")
public class GalleryItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "image")
    private final Uri imageUri;
    @ColumnInfo(name = "name")
    private final String imageName;

    public GalleryItem(Uri imageUri, String imageName) {
        this.imageUri = imageUri;
        this.imageName = imageName;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getImageName() {
        return imageName;
    }
}
