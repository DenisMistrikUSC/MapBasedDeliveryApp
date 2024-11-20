package com.example.uscdoordrinkteam65;


import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StartPageActivityTest {

    @Rule
    public ActivityTestRule<StartPageActivity> mActivityTestRule = new ActivityTestRule<>(StartPageActivity.class);

    @Test
    public void startPageActivityTest() {
    }
}
