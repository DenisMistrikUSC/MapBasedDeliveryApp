package com.example.uscdoordrinkteam65;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ConfirmCheckoutCartButtonTest {

    @Rule
    public ActivityScenarioRule<TestActivity> mActivityTestRule = new ActivityScenarioRule<>(TestActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void checkoutConfirmCartButtonTest() {
        SystemClock.sleep(1500);
        ViewInteraction button = onView(
                allOf(withId(R.id.list),
                        isDisplayed()));
        button.perform(click());
        SystemClock.sleep(1500);
        ViewInteraction button1 = onView(
                allOf(withId(R.id.addcart), withText("ADD TO CART"),
                        isDisplayed()));
        button1.perform(click());
        SystemClock.sleep(1500);

        ViewInteraction button2 = onView(
                allOf(withId(R.id.cart2), withText("Confirm Cart and Checkout"),
                        isDisplayed()));
        button2.perform(click());
        SystemClock.sleep(1500);
        ViewInteraction checkout = onView(
                allOf(withId(R.id.checkout), withText("Checkout"),
                        isDisplayed()));
        checkout.perform(click());
        SystemClock.sleep(1500);
        ViewInteraction confirmCheckout = onView(
                allOf(withId(R.id.confirmCheckout), withText("Confirm Checkout"),
                        isDisplayed()));
        confirmCheckout.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

