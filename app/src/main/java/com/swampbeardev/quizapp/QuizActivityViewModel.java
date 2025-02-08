package com.swampbeardev.quizapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Random;

public class QuizActivityViewModel extends ViewModel {

    private QuizApplication app;
    private ArrayList<GalleryItem> galleryItems;

    private final MutableLiveData<GalleryItem> currentItem = new MutableLiveData<>();
    private final MutableLiveData<String> correctAnswer = new MutableLiveData<>();
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> questionNumber = new MutableLiveData<>(0);

    private final Random random = new Random();
    private final Handler handler = new Handler();

    public void setApp(QuizApplication app) {
        this.app = app;
        this.galleryItems = app.getGalleryItems();
    }

    public QuizApplication getApp() {
        return app;
    }

    public ArrayList<GalleryItem> getGalleryItems() {
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
}
