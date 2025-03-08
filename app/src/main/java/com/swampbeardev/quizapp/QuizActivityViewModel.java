package com.swampbeardev.quizapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.os.Handler;

import java.util.List;
import java.util.Random;

public class QuizActivityViewModel extends ViewModel {

    private LiveData<List<GalleryItem>> galleryItems;

    private final MutableLiveData<GalleryItem> currentItem = new MutableLiveData<>();
    private final MutableLiveData<String> correctAnswer = new MutableLiveData<>();
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> questionNumber = new MutableLiveData<>(0);

    private final MutableLiveData<List<String>> currentOptions = new MutableLiveData<>();

    private final Random random = new Random();
    private final Handler handler = new Handler();

    public void setApp(@NonNull  Application app) {
        GalleryItemRepository repository = new GalleryItemRepository(app);
        this.galleryItems = repository.getAllGalleryItems();
    }

    public LiveData<List<GalleryItem>> getGalleryItems() {
        return galleryItems;
    }

    public LiveData<GalleryItem> getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(GalleryItem item) {
        currentItem.setValue(item);
    }

    public LiveData<String> getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String answer) {
        correctAnswer.setValue(answer);
    }

    public LiveData<Integer> getScore() {
        return score;
    }

    public void incrementScore() {
        if (score.getValue() != null) {
            score.setValue(score.getValue() + 1);
        }
    }

    public LiveData<Integer> getQuestionNumber() {
        return questionNumber;
    }

    public void incrementQuestionNumber() {
        if (questionNumber.getValue() != null) {
            questionNumber.setValue(questionNumber.getValue() + 1);
        }
    }

    public Random getRandom() {
        return random;
    }

    public Handler getHandler() {
        return handler;
    }
    public LiveData<List<String>> getCurrentOptions() {
        return currentOptions;
    }
    public void setCurrentOptions(List<String> options) {
        currentOptions.setValue(options);
    }
}
