package com.takagimeow.adaptivelayout

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.takagimeow.adaptivelayout.core.testing.RobotTestRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AdaptiveLayoutBottomNavigationBarTest {
    @get:Rule val robotTestRule = RobotTestRule(this)
    @Inject lateinit var adaptiveLayoutBottomNavigationBarRobot: AdaptiveLayoutBottomNavigationBarRobot

    @Test
    fun test_destination_icons_displayed() {
        adaptiveLayoutBottomNavigationBarRobot(robotTestRule) {
            checkIconsDisplayed()
        }
    }
}