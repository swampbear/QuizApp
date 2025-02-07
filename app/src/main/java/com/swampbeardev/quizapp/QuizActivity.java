package com.swampbeardev.quizapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Activity for the quiz screen.
 */
public class QuizActivity extends AppCompatActivity {

    private QuizApplication app;
    private ArrayList<GalleryItem> galleryItems;

    private ImageView imageView;
    private Button option1, option2, option3, endQuiz;
    private TextView feedbackText, scoreText;

    private GalleryItem currentItem;
    private String correctAnswer;
    private int score = 0;
    private int questionNumber = 0;
    private Random random = new Random();
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        app = (QuizApplication) getApplication();
        galleryItems = app.getGalleryItems();
        imageView = findViewById(R.id.imageView);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        endQuiz = findViewById(R.id.end_button);
        feedbackText = findViewById(R.id.feedbackText);
        scoreText = findViewById(R.id.scoreText);
        if (galleryItems.size() < 3) {
            displayToFewEntries();
            return;
        }


        loadNewQuestion();
        endQuiz.setOnClickListener(view -> {
            finish();
        });

    }

    /**
     * Displays a message if there are not enough entries in the gallery.
     */
    private void displayToFewEntries() {
        feedbackText.setText("There are not enough entries in the gallery to play the quiz.");
        feedbackText.setBackgroundColor(ContextCompat.getColor(this, R.color.incorrectBackground));
        option1.setVisibility(View.INVISIBLE);
        option2.setVisibility(View.INVISIBLE);
        option3.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2500);
    }

    /**
     * Loads the next questions by selecting randomly between gallery items
     */
    private void loadNewQuestion() {
        enableButtons();
        feedbackText.setText("");
        int randomIndex = random.nextInt(app.getGalleryItems().size());
        currentItem = galleryItems.get(randomIndex);
        correctAnswer = currentItem.getImageName();
        imageView.setImageURI(currentItem.getImageUri());
        ArrayList<String> options = new ArrayList<>();
        options.add(correctAnswer);
        while (options.size() < 3) {
            String option = galleryItems.get(random.nextInt(galleryItems.size())).getImageName();
            if (!options.contains(option)) {
                options.add(option);
            }
        }
        Collections.shuffle(options);
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
        questionNumber++;
        setOptionClickListener(option1);
        setOptionClickListener(option2);
        setOptionClickListener(option3);
    }
    /**
     * Sets the click listener for the options.
     * @param button The button to set the listener for.
     */
    private void setOptionClickListener(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAnswer(button.getText().toString());
            }
        });
    }
    /**
     * Evaluates the answer and updates the score.
     * @param selectedOption The selected option.
     */
    private void evaluateAnswer(String selectedOption) {
        if (selectedOption.equals(correctAnswer)) {
            feedbackText.setText("Correct!");
            feedbackText.setBackgroundColor(ContextCompat.getColor(this, R.color.correctBackground));
            score++;
        } else {
            feedbackText.setText("Incorrect. The correct answer was: " + correctAnswer);
            feedbackText.setBackgroundColor(ContextCompat.getColor(this, R.color.incorrectBackground));
        }
        updateScoreText();
        disableButtons();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                feedbackText.setBackgroundColor(ContextCompat.getColor(QuizActivity.this, android.R.color.transparent));
                loadNewQuestion();
            }
        }, 1500);
    }

    /**
     * Updates the score text.
     */
    private void updateScoreText() {
        scoreText.setText("Score: " + score + "/" + questionNumber);
    }

    /**
     * Disables the options.
     */
    private void disableButtons() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
    }
    /**
     * Enables the options.
     */
    private void enableButtons() {
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
    }
}
