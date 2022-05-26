package com.example.mymoviedb


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun registerTest() {
        val materialTextView = onView(
            allOf(
                withId(R.id.tv_register), withText("Not register yet?"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_controller),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.et_username),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.ly_username),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("agus1"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.ly_real_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("agus1"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.et_password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.ly_datepicker_birthday),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("agus1"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.et_email), withText("agus1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.ly_real_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("agus1@gmail.com"))

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.et_email), withText("agus1@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.ly_real_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(closeSoftKeyboard())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.et_confirm_password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.ly_address),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("agus1"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.btn_register), withText("Register"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_controller),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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
}
