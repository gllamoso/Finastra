package dev.gtcl.finastra.tests

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import dev.gtcl.finastra.R
import dev.gtcl.finastra.view.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTest {

    // Fragment needs MainActivity to prevent crashes
    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(5000)
    }

    @Test
    fun testListFragment() {
        onView(ViewMatchers.withId(R.id.recycler_view)).apply {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            check(RecyclerViewListNotEmptyAssertion())
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
}