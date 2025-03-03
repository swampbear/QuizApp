package com.swampbeardev.quizapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Collections;

public class QuizFragment extends Fragment {

    private QuizActivityViewModel viewModel;
    private ImageView imageView;
    private Button option1, option2, option3, endQuiz;
    private TextView feedbackText, scoreText;

    public QuizFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout. Place your portrait layout in res/layout/fragment_quiz.xml
        // and your landscape layout in res/layout-land/fragment_quiz.xml.
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Initialize the view model and UI components
        viewModel = new ViewModelProvider(requireActivity()).get(QuizActivityViewModel.class);
        if (viewModel.getApp() == null) {
            viewModel.setApp((QuizApplication) requireActivity().getApplication());
        }

        imageView = view.findViewById(R.id.imageView);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        endQuiz = view.findViewById(R.id.end_button);
        feedbackText = view.findViewById(R.id.feedbackText);
        scoreText = view.findViewById(R.id.scoreText);

        if (viewModel.getGalleryItems().size() < 3) {
            displayTooFewEntries();
            return;
        }

        viewModel.getCurrentItem().observe(getViewLifecycleOwner(), item -> {
            if (item != null) {
                imageView.setImageURI(item.getImageUri());
            }
        });

        viewModel.getScore().observe(getViewLifecycleOwner(), score -> updateScoreText());
        viewModel.getQuestionNumber().observe(getViewLifecycleOwner(), questionNum -> {
            // You can update question-related UI here if needed.
        });

        loadNewQuestion();

        endQuiz.setOnClickListener(v -> requireActivity().finish());
    }

    @SuppressLint("SetTextI18n")
    private void displayTooFewEntries() {
        feedbackText.setText("There are not enough entries in the gallery to play the quiz.");
        feedbackText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.incorrectBackground));
        option1.setVisibility(View.INVISIBLE);
        option2.setVisibility(View.INVISIBLE);
        option3.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);
        viewModel.getHandler().postDelayed(() -> requireActivity().finish(), 2500);
    }

    /**
     * Loads the next question by randomly selecting a gallery item.
     */
    private void loadNewQuestion() {
        enableButtons();
        feedbackText.setText("");

        int randomIndex = viewModel.getRandom().nextInt(viewModel.getGalleryItems().size());
        GalleryItem currentItem = viewModel.getGalleryItems().get(randomIndex);
        viewModel.setCurrentItem(currentItem);
        viewModel.setCorrectAnswer(currentItem.getImageName());

        ArrayList<String> options = new ArrayList<>();
        options.add(currentItem.getImageName());

        while (options.size() < 3) {
            String option = viewModel.getGalleryItems().get(viewModel.getRandom().nextInt(viewModel.getGalleryItems().size())).getImageName();
            if (!options.contains(option)) {
                options.add(option);
            }
        }
        Collections.shuffle(options);

        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));

        setOptionClickListener(option1);
        setOptionClickListener(option2);
        setOptionClickListener(option3);
    }

    private void setOptionClickListener(final Button button) {
        button.setOnClickListener(v -> evaluateAnswer(button.getText().toString()));
    }

    @SuppressLint("SetTextI18n")
    private void evaluateAnswer(String selectedOption) {
        if (selectedOption.equals(viewModel.getCorrectAnswer().getValue())) {
            feedbackText.setText("Riktig!");
            feedbackText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctBackground));
            viewModel.incrementScore();
        } else {
            feedbackText.setText("Feil.. Rett svar var: " + viewModel.getCorrectAnswer().getValue());
            feedbackText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.incorrectBackground));
        }
        viewModel.incrementQuestionNumber();
        updateScoreText();
        disableButtons();
        viewModel.getHandler().postDelayed(() -> {
            feedbackText.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent));
            loadNewQuestion();
        }, 1500);
    }

    @SuppressLint("SetTextI18n")
    private void updateScoreText() {
        scoreText.setText("Resultat: " + viewModel.getScore().getValue() + "/" + viewModel.getQuestionNumber().getValue());
    }

    private void disableButtons() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
    }

    private void enableButtons() {
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
    }
}
