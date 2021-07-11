package dev.gtcl.finastra.view


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import dev.gtcl.finastra.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(5000)
    }

    // Needs to be put here. Testing ListFragment individually causes crashes
    @Test
    fun testListFragment() {
        onView(withId(R.id.recycler_view)).apply {
            check(matches(isDisplayed()))
            check(RecyclerViewListNotEmptyAssertion())
        }
    }

    @Test
    fun navigationTest() {
        val firstActionbar = onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withResourceName("action_bar")
                )
            )
        )
        firstActionbar.check(matches(withText(getResourceString(R.string.list_fragment_label, 0))))

        val firstPhotoView = onView(
            allOf(
                withId(R.id.card_view),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        firstPhotoView.perform(click())

        val detailsActionBar = onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar"))))
        detailsActionBar.check(matches(withText(getResourceString(R.string.details_fragment_label))))

        val detailsBackButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        detailsBackButton.perform(click())
        firstActionbar.check(matches(isDisplayed()))
        firstPhotoView.check(matches(isDisplayed()))

        val firstNextSolButton = onView(
            allOf(
                withId(R.id.next), withContentDescription("Next Sol"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        firstNextSolButton.perform(click())
        val nextSolActionBar = onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar"))))
        nextSolActionBar.check(matches(withText(getResourceString(R.string.list_fragment_label, 1))))

        val nextSolBackButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        nextSolBackButton.perform(click())
        firstActionbar.check(matches(isDisplayed()))
        firstPhotoView.check(matches(isDisplayed()))
        firstNextSolButton.check(matches(isDisplayed()))
    }

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    class RecyclerViewListNotEmptyAssertion: ViewAssertion {

        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assert(adapter?.itemCount ?: 0 > 1)
        }
    }

    private fun getResourceString(id: Int, vararg formatArgs: Any): String {
        val context = ApplicationProvider.getApplicationContext() as Context
        return context.getString(id, *formatArgs)
    }
}
