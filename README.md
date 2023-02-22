# AdaptiveLayout Composable

[![Release](https://jitpack.io/v/takagimeow/adaptive-layout.svg)](https://jitpack.io/#takagimeow/adaptive-layout)

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Jetpack Compose Composable for easy implementation of Adaptive Layout.

Normally, implementing adaptive layouts with Jetpack Compose requires a lot of work.

But with this composable, all you have to prepare is the content you want to show.

The composable takes care of the complicated work required to implement adaptive layouts.

## Samples

| Device     |                                                                                                            |
|------------|------------------------------------------------------------------------------------------------------------|
| Smartphone | ![](https://user-images.githubusercontent.com/66447334/220685929-58297e92-9475-47c1-b6cd-1554aa4a5976.png) |
| Foldable   | ![](https://user-images.githubusercontent.com/66447334/220685979-8bf5c653-5a35-4be2-91b1-e6cc9a950c41.png) |
| Tablet     | ![](https://user-images.githubusercontent.com/66447334/220686029-208c70dd-bbb1-40b2-8e6b-bde4152cccc5.png) |

## Usage

It is very easy to use.

All you have to do is prepare each route that should be associated with each icon.
Then, simply pass the methods and classes exposed by the library directly to AdaptiveLayout Composable.
As a result, you will be able to build screens with layouts for various screen sizes.

See? Easy, isn't it?

```kotlin
object HomeDestination : AdaptiveLayoutNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

object SettingsDestination : AdaptiveLayoutNavigationDestination {
    override val route = "settings_route"
    override val destination = "settings_destination"
}

object MyApplicationIcons {
    val Home = Icons.Outlined.Home
    val HomeFilled = Icons.Filled.Home
    val Settings = Icons.Outlined.Settings
    val SettingsFilled = Icons.Filled.Settings
}
val HomeTopLevelDestination: AdaptiveLayoutTopLevelDestination = AdaptiveLayoutTopLevelDestination(
    route = HomeDestination.route,
    destination = HomeDestination.destination,
    selectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(MyApplicationIcons.HomeFilled),
    unselectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(MyApplicationIcons.Home),
    iconTextId = R.string.home
)

val SettingsTopLevelDestination: AdaptiveLayoutTopLevelDestination = AdaptiveLayoutTopLevelDestination(
    route = SettingsDestination.route,
    destination = SettingsDestination.destination,
    selectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(MyApplicationIcons.SettingsFilled),
    unselectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(MyApplicationIcons.Settings),
    iconTextId = R.string.settings
)


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
                    background = { _, content ->
                        content()
                    },
                ) { navController, navigate, navigateAndPopUp ->
                    MyApplicationNavGraph(
                        navController = navController,
                        startDestination = HomeDestination.route,
                        onNavigateAndPopUpToDestination = navigateAndPopUp,
                        onNavigateToDestination = navigate,
                    )
                }
            }
        }
    }
}
```

The `LocalContentType` can then be accessed inside composables.

This allows the developer to decide if only the list should be displayed on the current device, or if both the list and the detail screen should be displayed.

```kotlin
@Composable
fun HomeRoute() {
    val isListAndDetail = (LocalContentType.current == AdaptiveLayoutContentType.LIST_AND_DETAIL)

    HomeScreen(
        isListAndDetail = isListAndDetail,
    )
}

@Composable
fun HomeScreen(
    isListAndDetail: Boolean,
) {
    val activity = LocalContext.current
    val displayFeatures = calculateDisplayFeatures(activity as Activity)

    if(isListAndDetail) {
        TwoPane(
            first = {
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Home Screen - First")
                    }
                }
            },
            second = {
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Home Screen - Second")
                    }
                }
            },
            strategy = HorizontalTwoPaneStrategy(
                splitFraction = 1f / 3f,
            ),
            displayFeatures = displayFeatures,
            foldAwareConfiguration = FoldAwareConfiguration.VerticalFoldsOnly,
            modifier = Modifier.padding(8.dp)
        )
    } else {
        Card(
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Home Screen - Single Pane")
            }
        }
    }
}
```

Please check the app directory for detailed implementation instructions.

## Setup

This chapter will show you how to add libraries to your project.

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

If the above configuration fails, add the following setting to settings.gradle.

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2. Add the dependency

```groovy
dependencies {
    implementation 'com.github.takagimeow:adaptive-layout:0.4.1'
}
```
