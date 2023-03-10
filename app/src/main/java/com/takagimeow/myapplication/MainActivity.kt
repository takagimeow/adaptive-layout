package com.takagimeow.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.takagimeow.adaptivelayout.AdaptiveLayout
import com.takagimeow.myapplication.feature.splash.navigation.SplashDestination
import com.takagimeow.myapplication.ui.HomeTopLevelDestination
import com.takagimeow.myapplication.ui.MyApplicationNavGraph
import com.takagimeow.myapplication.ui.SettingsTopLevelDestination
import com.takagimeow.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                AdaptiveLayout(
                    topLevelDestinations = listOf(
                        HomeTopLevelDestination,
                        SettingsTopLevelDestination,
                    ),
                    background = { route, content ->
                        when (route) {
                            HomeTopLevelDestination.destination -> {
                                content()
                            }

                            SettingsTopLevelDestination.destination -> {
                                content()
                            }

                            else -> {
                                content()
                            }
                        }
                    },
                ) { navController, navigate, navigateAndPopUp ->
                    MyApplicationNavGraph(
                        navController = navController,
                        startDestination = SplashDestination.route,
                        onNavigateAndPopUpToDestination = navigateAndPopUp,
                        onNavigateToDestination = navigate,
                    )
                }
            }
        }
    }
}
