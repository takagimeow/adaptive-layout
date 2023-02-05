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
class AdaptiveLayoutAppMediumAndNormalPostureTest {
    @get:Rule val robotTestRule = RobotTestRule(this)
    @Inject lateinit var adaptiveLayoutAppMediumAndNormalPostureRobot: AdaptiveLayoutAppMediumAndNormalPostureRobot

    @Test
    fun test_rail_displayed() {
        adaptiveLayoutAppMediumAndNormalPostureRobot(robotTestRule) {
            checkNavigationRailDisplayed()
        }
    }

    @Test
    fun test_icon_becomes_selected_when_clicked() {
        adaptiveLayoutAppMediumAndNormalPostureRobot(robotTestRule) {
            ensureIconBecomesSelectedWhenClicked()
        }
    }

    @Test
    fun test_drawer_displayed() {
        adaptiveLayoutAppMediumAndNormalPostureRobot(robotTestRule) {
            ensureDrawerDisplayed()
        }
    }
}