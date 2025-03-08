package com.swampbeardev.quizapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.Handler;
import android.os.Looper;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QuizScoreUpdateTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityRule = new ActivityScenarioRule<>(QuizActivity.class);

    private QuizActivityViewModel viewModel;

    @Before
    public void setup(){
        activityRule.getScenario().onActivity(activity -> {
            viewModel = new QuizActivityViewModel();
            viewModel.setApp(activity.getApplication());
        });
    }

    @Test
    public void testCorrectAnswerIncrementsScore(){
        String correctAnswer = viewModel.getCorrectAnswer().getValue();

        if(correctAnswer != null) {
            if (!correctAnswer.equals(getButtonText(R.id.option1))) {
                onView(withId(R.id.option1)).perform(click());
            } else if (!correctAnswer.equals(getButtonText(R.id.option2))) {
                onView(withId(R.id.option2)).perform(click());
            } else if (!correctAnswer.equals(getButtonText(R.id.option3))) {
                onView(withId(R.id.option3)).perform(click());
            }
        }

        int expectedScore = viewModel.getScore().getValue();
        int expectedQuestionNum = viewModel.getQuestionNumber().getValue();

        onView(withId(R.id.scoreText))
                .check(matches(withText("Score: " + expectedScore + "/" + expectedQuestionNum)));
    }

    @Test
    public void testIncorrectAnswerDoesNotIncrementScore() {
        String correctAnswer = viewModel.getCorrectAnswer().getValue();

        if (correctAnswer != null) {
            if (!correctAnswer.equals(getButtonText(R.id.option1))) {
                onView(withId(R.id.option1)).perform(click());
            } else if (!correctAnswer.equals(getButtonText(R.id.option2))) {
                onView(withId(R.id.option2)).perform(click());
            } else if (!correctAnswer.equals(getButtonText(R.id.option3))) {
                onView(withId(R.id.option3)).perform(click());
            }
        }

        int expectedScore = viewModel.getScore().getValue();
        int expectedQuestionNum = viewModel.getQuestionNumber().getValue();

        onView(withId(R.id.scoreText))
                .check(matches(withText("Score: " + expectedScore + "/" + expectedQuestionNum)));
    }

    private String getButtonText(int buttonId){
        final String[] text = {""};
        activityRule.getScenario().onActivity(activity -> {
            text[0] = activity.findViewById(buttonId).toString();
        });
        return text[0];
    }
}
