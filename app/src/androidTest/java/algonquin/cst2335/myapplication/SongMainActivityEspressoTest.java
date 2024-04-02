package algonquin.cst2335.myapplication;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;

public class SongMainActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<SongMainActivity> activityScenarioRule = new ActivityScenarioRule<>(SongMainActivity.class);

    @Test
    public void testSearchButtonVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.search_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchEditTextVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.search_edit_text))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchFunctionality() {
        String searchQuery = "test";
        Espresso.onView(ViewMatchers.withId(R.id.search_edit_text))
                .perform(ViewActions.typeText(searchQuery), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.search_button))
                .perform(ViewActions.click());
        // Add assertions here to verify the search results
    }

}