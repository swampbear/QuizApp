package com.swampbeardev.quizapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Activity for the quiz screen.
 */
public class QuizActivity extends AppCompatActivity {
//    private QuizApplication app;
//    private ArrayList<GalleryItem> galleryItems;
//    private GalleryItem currentItem;
//    private String correctAnswer;
//    private int score = 0;
//    private int questionNumber = 0;
//    private Random random = new Random();
//    private final Handler handler = new Handler();
    private QuizActivityViewModel viewModel;
    private ImageView imageView;
    private Button option1;
    private Button option2;
    private Button option3;
    private TextView feedbackText, scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        viewModel = new ViewModelProvider(this).get(QuizActivityViewModel.class);
        if (viewModel.getApp() == null) {
            viewModel.setApp((QuizApplication) getApplication());
        }
//        app = (QuizApplication) getApplication();
//        galleryItems = app.getGalleryItems();
        imageView = findViewById(R.id.imageView);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        Button endQuiz = findViewById(R.id.end_button);
        feedbackText = findViewById(R.id.feedbackText);
        scoreText = findViewById(R.id.scoreText);

        if (viewModel.getGalleryItems().size() < 3) {
            displayToFewEntries();
            return;
        }
//        if (galleryItems.size() < 3) {
//            displayToFewEntries();
//            return;
//        }
        viewModel.getCurrentItem().observe(this, item -> {
            if (item != null) {
                imageView.setImageURI(item.getImageUri());
            }
        });
        viewModel.getCorrectAnswer().observe(this, answer -> {
            // pass
        });
        viewModel.getScore().observe(this, score -> updateScoreText());
        viewModel.getQuestionNumber().observe(this, questionNum -> {
            // pass
        });

        loadNewQuestion();
        endQuiz.setOnClickListener(view -> finish());
    }

    /**
     * Displays a message if there are not enough entries in the gallery.
     */
    @SuppressLint("SetTextI18n")
    private void displayToFewEntries() {
        feedbackText.setText("There are not enough entries in the gallery to play the quiz.");
        feedbackText.setBackgroundColor(ContextCompat.getColor(this, R.color.incorrectBackground));
        option1.setVisibility(View.INVISIBLE);
        option2.setVisibility(View.INVISIBLE);
        option3.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//            }
//        }, 2500);
        viewModel.getHandler().postDelayed(this::finish, 2500);
    }

    /**
     * Loads the next questions by selecting randomly between gallery items
     */
    private void loadNewQuestion() {
        enableButtons();
        feedbackText.setText("");

//        int randomIndex = random.nextInt(app.getGalleryItems().size());
        int randomIndex = viewModel.getRandom().nextInt(viewModel.getGalleryItems().size());
//        currentItem = galleryItems.get(randomIndex);
//        correctAnswer = currentItem.getImageName();
//        imageView.setImageURI(currentItem.getImageUri());
        GalleryItem currentItem = viewModel.getGalleryItems().get(randomIndex);
        viewModel.setCurrentItem(currentItem);
        viewModel.setCorrectAnswer(currentItem.getImageName());

        ArrayList<String> options = new ArrayList<>();

//        options.add(correctAnswer);
        options.add(currentItem.getImageName());

        while (options.size() < 3) {
//            String option = galleryItems.get(random.nextInt(galleryItems.size())).getImageName();
            String option = viewModel.getGalleryItems().get(viewModel.getRandom().nextInt(viewModel.getGalleryItems().size())).getImageName();
            if (!options.contains(option)) {
                options.add(option);
            }
        }
        Collections.shuffle(options);

        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));

//        questionNumber++;
        viewModel.incrementQuestionNumber();

        setOptionClickListener(option1);
        setOptionClickListener(option2);
        setOptionClickListener(option3);
    }

    /**
     * Sets the click listener for the options.
     *
     * @param button The button to set the listener for.
     */
    private void setOptionClickListener(final Button button) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                evaluateAnswer(button.getText().toString());
//            }
//        });
        button.setOnClickListener(v -> evaluateAnswer(button.getText().toString()));
    }

    /**
     * Evaluates the answer and updates the score.
     *
     * @param selectedOption The selected option.
     */
    @SuppressLint("SetTextI18n")
    private void evaluateAnswer(String selectedOption) {
//        if (selectedOption.equals(correctAnswer)) {
        if (selectedOption.equals(viewModel.getCorrectAnswer().getValue())) {
            feedbackText.setText("Riktig!");
            feedbackText.setBackgroundColor(ContextCompat.getColor(this, R.color.correctBackground));
//            score++;
            viewModel.incrementScore();
        } else {
//            feedbackText.setText("Incorrect. The correct answer was: " + correctAnswer);
            feedbackText.setText("Feil.. Rett svar var: " + viewModel.getCorrectAnswer().getValue());
            feedbackText.setBackgroundColor(ContextCompat.getColor(this, R.color.incorrectBackground));
        }

        updateScoreText();
        disableButtons();

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                feedbackText.setBackgroundColor(ContextCompat.getColor(QuizActivity.this, android.R.color.transparent));
//                loadNewQuestion();
//            }
//        }, 1500);
        viewModel.getHandler().postDelayed(() -> {
            feedbackText.setBackgroundColor(ContextCompat.getColor(QuizActivity.this, android.R.color.transparent));
            loadNewQuestion();
        }, 1500);
    }

    /**
     * Updates the score text.
     */
    @SuppressLint("SetTextI18n")
    private void updateScoreText() {
        scoreText.setText("Resultat: " + viewModel.getScore().getValue() + "/" + viewModel.getQuestionNumber().getValue());
    }
//    private void updateScoreText() {
//        scoreText.setText("Score: " + score + "/" + questionNumber);
//    }

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
