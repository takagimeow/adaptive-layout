# AdaptiveLayout Composable

[![Release](https://jitpack.io/v/takagimeow/adaptive-layout.svg)](https://jitpack.io/#takagimeow/adaptive-layout)

Jetpack Compose Composable for easy implementation of Adaptive Layout.

Normally, implementing adaptive layouts with Jetpack Compose requires a lot of work.

But with this composable, all you have to prepare is the content you want to show.

The composables take care of the complicated work required to implement adaptive layouts.

## Samples

| Device | |
| --- | --- |
| Smartphone |![](https://user-images.githubusercontent.com/66447334/216823094-4e4c7b05-7ece-428f-9383-4dd28447bb96.png)|
| Foldable|![](https://user-images.githubusercontent.com/66447334/216823109-5a0bd223-d453-47fd-8637-d9ec721b697f.png)|
| Tablet |![](https://user-images.githubusercontent.com/66447334/216823121-f5b41ad2-2765-45e1-a6bc-d5f0d9d10c2c.png)|

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
    implementation 'com.github.takagimeow:adaptive-layout:0.1.3'
}
```
