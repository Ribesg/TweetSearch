package fr.ribesg.tweetsearch

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsInstanceOf
import org.hamcrest.core.StringEndsWith.endsWith
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    private val query = "Test Query"

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SearchActivity::class.java)

    @Before
    fun before() {
        Intents.init()
    }

    @Test
    fun search() {
        // Type query
        onView(withId(R.id.activity_search_field))
            .perform(typeText(query), closeSoftKeyboard())
        // Checked that field content matches query
        onView(withId(R.id.activity_search_field))
            .check(matches(withText(query)))
        // Click on search button
        onView(withId(R.id.activity_search_button))
            .perform(click())
        // Check that ResultsActivity has started
        intended(IntentMatchers.hasComponent(ResultsActivity::class.java.name))
        // Check that ResultsActivity ActionBar contains query
        onView(allOf(IsInstanceOf.instanceOf(TextView::class.java), withParent(withResourceName("action_bar"))))
            .check(matches(withText(endsWith(query))))
    }

}
