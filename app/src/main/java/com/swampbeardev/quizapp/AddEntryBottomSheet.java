package com.swampbeardev.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class AddEntryBottomSheet extends BottomSheetDialogFragment {

    private Uri selectedImageUri;
    private ActivityResultLauncher<String> getContentLauncher;
    private GalleryAdapter adapter;

    public AddEntryBottomSheet(GalleryAdapter adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_layout, null);
        dialog.setContentView(view);
        EditText nameInput = view.findViewById(R.id.nameInput);
        ImageView selectedImage = view.findViewById(R.id.selectedImage);
        Button uploadImageButton = view.findViewById(R.id.uploadImageButton);
        Button saveButton = view.findViewById(R.id.saveButton);

        getContentLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        try {
                            getActivity().getContentResolver().takePersistableUriPermission(uri, takeFlags);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                        }

                        selectedImageUri = uri;
                        selectedImage.setImageURI(uri);
                        selectedImage.setVisibility(View.VISIBLE);
                    }
                }
        );


        uploadImageButton.setOnClickListener(v -> {
            // Launch the gallery to select an image
            getContentLauncher.launch("image/*");
        });

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            if (!name.isEmpty() && selectedImageUri != null) {
                Uri imageUri = selectedImageUri;
                if (adapter != null) {
                    GalleryItem newItem = new GalleryItem(selectedImageUri, name);
                    adapter.addEntry(newItem);
                } else {
                    // Handle the case where galleryAdapter is null, e.g., log an error
                    Log.e("AddEntryBottomSheet", "galleryAdapter is null!");
                }
                Toast.makeText(getContext(), "Entry added:" + name + " " + imageUri, Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getContext(), "Please enter a name and select an image", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;
    }
}
