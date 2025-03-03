package com.swampbeardev.quizapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter for the gallery RecyclerView.
 * The adapter is responsible for binding the gallery items to the RecyclerView.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    List<GalleryItem> galleryItems;
    private OnClickListener listener;

    public GalleryAdapter(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }

    /**
     * Interface for handling item click events.
     */
    public interface OnClickListener {
        void onItemDelete(int position);
    }

    /**
     * Sets the click listener for the adapter.
     *
     * @param listener The click listener to set.
     */
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Adds a new gallery item to the adapter.
     *
     * @param item The gallery item to add.
     */
    public void addEntry(GalleryItem item) {
        this.galleryItems.add(item);
        Log.d(item.getImageName(), "addEntry: ");
        notifyItemInserted(galleryItems.size() - 1);
    }

    public void setGalleryItems(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
        notifyDataSetChanged();
    }

    public List<GalleryItem> getGalleryItems() {
        return galleryItems;
    }
    /**
     * Removes a gallery item from the adapter.
     *
     * @param item The gallery item to remove.
     */
    public void removeEntry(GalleryItem item) {
        int position = galleryItems.indexOf(item);
        if (position != -1) {
            galleryItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Sorts the gallery items in an alphabetical order
     */
    public void sortEntriesAZ() {
        Collections.sort(galleryItems, Comparator.comparing(GalleryItem::getImageName));
        notifyDataSetChanged();
    }
    /**
     * Sorts the gallery items in a reverse alphabetical order
     */
    public void sortEntriesZA() {
        Collections.sort(galleryItems, Comparator.comparing(GalleryItem::getImageName).reversed());
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_entry, parent, false);
        return new GalleryViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        GalleryItem item = galleryItems.get(position);
        holder.imageView.setImageURI(item.getImageUri());
        holder.imageName.setText(item.getImageName());
    }

    @Override
    public int getItemCount() {
        if (galleryItems == null) {
            return 0;
        } else {
            return galleryItems.size();
        }
    }

    /**
     * ViewHolder for the gallery items.
     * Contains the ImageView and TextView for each item.
     * And are responsible for handling item click events.
     */
    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView imageName;
        ImageButton deleteButton = itemView.findViewById(R.id.buttonDelete);

        /**
         * Constructor for the GalleryViewHolder.
         *
         * @param itemView The view for the item.
         * @param listener The click listener for the item.
         */
        public GalleryViewHolder(@NonNull View itemView, final OnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageName = itemView.findViewById(R.id.textView);

            deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemDelete(position);
                    }
                }
            });
        }
    }
}
