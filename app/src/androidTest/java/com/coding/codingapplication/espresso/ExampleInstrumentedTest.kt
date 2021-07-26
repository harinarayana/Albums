package com.coding.codingapplication.espresso

import android.content.Context
import android.view.View
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coding.codingapplication.R
import com.coding.codingapplication.ui.AlbumAdapter
import com.coding.codingapplication.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    // Basic ActivityScenario rule for Launching
    @get:Rule(order = 1)
    public val activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun close() {
        activityScenarioRule.scenario.close()
    }



    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        // Attempt to scroll to an item that contains the special text.
        onView(ViewMatchers.withId(R.id.album_list)) // scrollTo will fail the test if no item matches.
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("not in the list"))
                )
            )
    }


    //ToDO when click action enabled for recyclerView
    /*@Test
    fun testCaseForRecyclerClick() {
        Espresso.onView(ViewMatchers.withId(R.id.album_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()))
    }*/

    @Test
    fun testRecyclerItemView() {
        // Check that the item has the special text.
        onView(withId(R.id.album_list))
            .check(matches(withViewAtPosition(0, isDisplayed())))
    }

    @Test
    fun testCaseForRecyclerScroll() {

        // Get total item of RecyclerView
        // Scroll to end of page with position
        Espresso.onView(ViewMatchers.withId(R.id.album_list))
            .perform(RecyclerViewActions.scrollToPosition<AlbumAdapter.ViewHolder>(10))
    }


    fun withViewAtPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}