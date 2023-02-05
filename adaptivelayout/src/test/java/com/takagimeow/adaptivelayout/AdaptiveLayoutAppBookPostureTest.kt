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
class AdaptiveLayoutAppBookPostureTest {
    @get:Rule val robotTestRule = RobotTestRule(this)
    @Inject lateinit var adaptiveLayoutAppBookPostureRobot: AdaptiveLayoutAppBookPostureRobot

    @Test
    fun test_rail_displayed() {
        adaptiveLayoutAppBookPostureRobot(robotTestRule) {
            checkNavigationRailDisplayed()
        }
    }

    @Test
    fun test_icon_becomes_selected_when_clicked() {
        adaptiveLayoutAppBookPostureRobot(robotTestRule) {
            ensureIconBecomesSelectedWhenClicked()
        }
    }
}