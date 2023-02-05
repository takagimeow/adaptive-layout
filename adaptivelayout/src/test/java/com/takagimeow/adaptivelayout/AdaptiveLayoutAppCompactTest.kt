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
class AdaptiveLayoutAppCompactTest {
    @get:Rule val robotTestRule = RobotTestRule(this)
    @Inject lateinit var adaptiveLayoutCompactRobot: AdaptiveLayoutAppCompactRobot

    @Test
    fun test_bottom_navigation_bar_displayed() {
        adaptiveLayoutCompactRobot(robotTestRule) {
            checkBottomNavigationBarDisplayed()
        }
    }

    @Test
    fun test_icon_becomes_selected_when_clicked() {
        adaptiveLayoutCompactRobot(robotTestRule) {
            ensureIconBecomesSelectedWhenClicked()
        }
    }
}