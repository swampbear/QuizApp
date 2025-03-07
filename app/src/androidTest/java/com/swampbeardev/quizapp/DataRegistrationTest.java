package com.swampbeardev.quizapp;

import static android.app.Activity.RESULT_OK;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(AndroidJUnit4.class)
public class DataRegistrationTest {

    @Rule
    public ActivityScenarioRule<GalleryActivity> activityRule = new ActivityScenarioRule<>(GalleryActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE);

    @Before
    public void setUp(){
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void testAddAndDeleteEntry(){
        int initialCount = getRecyclerViewItemCount();

        // STUBBING
        Uri testImageUri = Uri.parse("android.resource://com.swampbeardev.quizapp/" + R.drawable.jobjorn);
        Intent resultData = new Intent();
        resultData.setData(testImageUri);

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(RESULT_OK, resultData);
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_GET_CONTENT)).respondWith(result);

        onView(withId(R.id.addEntryButton)).perform(click());
        String TEST_IMAGE_NAME = "Random green sweatshirt guy";
        onView(withId(R.id.nameInput)).perform(replaceText(TEST_IMAGE_NAME));
        onView(withId(R.id.uploadImageButton)).perform(click());
        onView(withId(R.id.saveButton)).perform(click());

        int afterAddCount = getRecyclerViewItemCount();
        assert(afterAddCount == initialCount + 1);

        onView(withId(R.id.recyclerView)).perform(scrollToPosition(afterAddCount - 1));
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withText(TEST_IMAGE_NAME))));
        onView(withId(R.id.buttonDelete)).perform(click());

        int afterDeleteCount = getRecyclerViewItemCount();
        assert(afterDeleteCount == initialCount);
    }

    /**
     * Gets the current number of items in the RecyclerView.
     */
    private int getRecyclerViewItemCount() {
        AtomicInteger count = new AtomicInteger();
        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
            count.set(Objects.requireNonNull(recyclerView.getAdapter()).getItemCount());
        });
        return count.get();
    }
}
