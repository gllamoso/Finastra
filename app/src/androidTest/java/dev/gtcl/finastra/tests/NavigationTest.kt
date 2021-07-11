package dev.gtcl.finastra.tests

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import dev.gtcl.finastra.R
import dev.gtcl.finastra.childAtPosition
import dev.gtcl.finastra.getResourceString
import dev.gtcl.finastra.view.MainActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(5000)
    }

    @Test
    fun navigationTest() {
        val firstActionbar = onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.action_bar))
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


}
