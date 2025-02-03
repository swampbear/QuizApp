package com.swampbeardev.quizapp;

import android.net.Uri;

/**
 * Class representing a gallery item.
 * Containing the image URI and the image name.
 */
public class GalleryItem {
    private final Uri imageUri;
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
