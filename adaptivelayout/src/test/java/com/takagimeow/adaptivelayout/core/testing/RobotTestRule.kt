package com.takagimeow.adaptivelayout.core.testing

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.takagimeow.adaptivelayout.AdaptiveLayoutTopLevelDestination
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RobotTestRule(
    private val testInstance: Any
) : TestRule {

    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    override fun apply(base: Statement?, description: Description?): Statement {
        return RuleChain
            .outerRule(HiltAndroidAutoInjectRule(testInstance))
            .around(composeTestRule)
            .apply(base, description)
    }
}