package algonquin.cst2335.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;


import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

public class DictionaryMainActivityTest {

    @Rule
    public ActivityScenarioRule<DictionaryMainActivity> activityRule =
            new ActivityScenarioRule<>(DictionaryMainActivity.class);


    @Test
    public void testEditTextInput() {
        onView(withId(R.id.etSearchTerm)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.etSearchTerm)).check(matches(withText("test")));
    }

    @Test
    public void testSearchButtonClick() {
        onView(withId(R.id.etSearchTerm)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.btnSearch)).perform(click());
        // Add assertions to verify expected behavior after clicking search button
    }

    @Test
    public void testDefinitionsRecyclerView() {
        onView(withId(R.id.etSearchTerm)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.rvDefinitions)).check(matches(isDisplayed()));
    }
}


