package dev.gtcl.nasarover.tests

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import dev.gtcl.nasarover.R
import dev.gtcl.nasarover.childAtPosition
import dev.gtcl.nasarover.getResourceString
import dev.gtcl.nasarover.view.MainActivity
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
    fun navigateToNextSol() {
        val firstActionbar = onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.action_bar))
            )
        )
        firstActionbar.check(matches(withText(getResourceString(R.string.list_fragment_label, 0))))

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
        val nextSolActionBarText = onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.action_bar))))
        nextSolActionBarText.check(matches(withText(getResourceString(R.string.list_fragment_label, 1))))

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
        firstNextSolButton.check(matches(isDisplayed()))
    }

    @Test
    fun navigateToDetails(){
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

        val detailsActionBarText = onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.action_bar))))
        detailsActionBarText.check(matches(withText(getResourceString(R.string.details_fragment_label))))

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
    }



}
