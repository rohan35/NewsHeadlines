package com.example.newsapplication.ui.fragments

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.newsapplication.R
import com.example.newsapplication.ui.activities.TopHeadlinesActivity
import com.example.newsapplication.ui.adapters.TopHeadlinesRecyclerAdapter
import com.example.newsapplication.utility.EspressoIdlingResource
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class TopHeadlinesFragmentTest{

    @get:Rule
    val activityRule = ActivityScenarioRule(TopHeadlinesActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun a_test_isListFragmentVisible_onAppLaunch() {
        Espresso.onView(withId(R.id.rv_top_headlines))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun b_test_tapListItem_isItemClickable() {
        // Tap on first item
        Espresso.onView(withId(R.id.rv_top_headlines))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<TopHeadlinesRecyclerAdapter.HeadlinesViewHolder>(
                    1,
                    ViewActions.click()
                )
            )
    }

    @Test
    fun c_test_tapListItem_isDecriptionVisible()
    {
        // Tap on first item
        Espresso.onView(withId(R.id.rv_top_headlines))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<TopHeadlinesRecyclerAdapter.HeadlinesViewHolder>(
                    1,
                    ViewActions.click()
                )
            )

        Espresso.onView(withId(R.id.tv_description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}