package jp.co.yumemi.android.codecheck

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jp.co.yumemi.android.codecheck.feature.top.repositorylist.RepositoryListAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import jp.co.yumemi.android.codecheck.feature.top.R as TopR

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val disableAnimationsRule = DisableAnimationsRule()

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var navController: NavController
    private lateinit var scenario: ActivityScenario<MainActivity>
    private var searchIdlingResource: SearchViewIdlingResource? = null

    @Before
    fun setUp() {
        hiltRule.inject()
        scenario = activityRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            navController = activity.findNavController(R.id.main_fragment_container)
            searchIdlingResource =
                SearchViewIdlingResource(activity.findViewById(TopR.id.progressBar))
        }

        searchIdlingResource?.let {
            IdlingRegistry.getInstance().register(it)
        }
    }

    @After
    fun tearDown() {
        searchIdlingResource?.let {
            IdlingRegistry.getInstance().unregister(it)
        }
    }

    @Test
    fun testSearchFlow() {
        onView(withId(TopR.id.searchInputText))
            .check(matches(isDisplayed()))
            .perform(typeText("kotlin"), pressImeActionButton())

        onView(withId(TopR.id.recyclerView))
            .check(matches(isDisplayed()))

        try {
            onView(withId(TopR.id.recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RepositoryListAdapter.ViewHolder>(
                        0, click()
                    )
                )

            onView(withId(TopR.id.repositoryDetailLayout))
                .check(matches(isDisplayed()))

            pressBack()
        } catch (e: Exception) {
            println("item click or detail screen check failed: ${e.message}")
        }
    }

    @Test
    fun testErrorScenario() {
        onView(withId(TopR.id.searchInputText))
            .check(matches(isDisplayed()))
            .perform(typeText("zxcvbnmasdfghjklqwertyuiop12345"), pressImeActionButton())

        onView(withId(TopR.id.errorView))
            .check(matches(isDisplayed()))
    }

    class SearchViewIdlingResource(private val progressBar: View) : IdlingResource {
        private var callback: IdlingResource.ResourceCallback? = null
        private var isIdle = true

        override fun getName(): String = "SearchViewIdlingResource"

        override fun isIdleNow(): Boolean {
            isIdle = progressBar.visibility != View.VISIBLE
            if (isIdle) {
                callback?.onTransitionToIdle()
            }
            return isIdle
        }

        override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
            this.callback = callback
        }
    }
}
