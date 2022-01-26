![Butterfly](https://user-images.githubusercontent.com/24237865/150674436-d2713bf0-da35-4b75-8629-5c83d8500cd7.png)<br><br>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/GetStream/butterfly/actions/workflows/android.yml"><img alt="Build Status" src="https://github.com/GetStream/butterfly/actions/workflows/android.yml/badge.svg"/></a>
  <a href="https://getstream.github.io/butterfly/"><img alt="Dokka" src="preview/dokka-butterfly.svg"/></a>
</p><br>

<p align="center">
🦋 Butterfly helps you to build adaptive and responsive UIs for Android with Jetpack WindowManager. <br>
Also, it supports useful functions for Jetpack Compose and LiveData integration.

</p><br>

## Preview
<p align="center">
<img src="/preview/preview0.png" />
</p>

<details>
    <summary>🌗 See dark theme</summary>
    
### Dark Theme
<img src="/preview/preview1.png" />
    
</details>

## Demo Project

The demo project built with the [Stream Chat SDK for Jetpack Compose](https://getstream.io/chat/sdk/compose/).
It would be helpful to understand the demo project if you check out the links below:

- [Compose Chat Messaging Tutorial](https://getstream.io/chat/compose/tutorial/)
- [Introduction to dual-screen devices](https://docs.microsoft.com/en-us/dual-screen/introduction)
- [Unbundling the WindowManager](https://medium.com/androiddevelopers/unbundling-the-windowmanager-fa060adb3ce9)

## Download
[![Maven Central](https://img.shields.io/maven-central/v/io.getstream/butterfly.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.getstream%22%20AND%20a:%22butterfly%22)

### Gradle
Add the codes below to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```

Next, add the dependency below to your **module**'s `build.gradle` file.

```gradle
dependencies {
    implementation "io.getstream:butterfly:1.0.0"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2"
}
```

> Note: Butterfly includes [Jetpack WindowManager](https://developer.android.com/jetpack/androidx/releases/window) to compute window internally. So if you're using WindowManager in your project, please make sure your project uses the same version or exclude the dependency to adapt yours.

### SNAPSHOT 

<details>
 <summary>See how to import the snapshot</summary>

### Including the SNAPSHOT
Snapshots of the current development version of Butterfly are available, which track [the latest versions](https://oss.sonatype.org/content/repositories/snapshots/io/getstream/butterfly/).

To import snapshot versions on your project, add the code snippet below on your gradle file.
```Gradle
repositories {
   maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }
}
```

Next, add the below dependency to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "io.getstream:butterfly:1.0.1-SNAPSHOT"
}
```

</details>

## Set up Foldable Emulator

<a href="https://docs.microsoft.com/en-us/dual-screen/introduction">
   <img src="/preview/instruction4.png" />
</a>

If you don't have foldable devices or emulators, you can set up a foldable emulator environment following the below instruction:

👉 Check out the [Set up Foldable Emulator (Surface Duo 2)](/INSTRUCTION_FOLDABLE.md)

## Usage

Butterfly uses [Jetpack WindowManager](https://developer.android.com/jetpack/androidx/releases/window?gclid=Cj0KCQiAuP-OBhDqARIsAD4XHpeYC6wgI0kSXjzRVoTdamCppreFdJdNsYr0xPIuQdqq-tzjzSM5-hEaAifuEALw_wcB),
so it would be helpful to understand if you have background knowledge of the **WindowManager** APIs.

### WindowSize

WindowSize represents breakpoints, which are the screen size at which a layout will adapt to best fit content and conform to responsive layout requirements. 
Butterfly follows three breakpoints by [Material Design](https://m3.material.io/foundations/adaptive-design/large-screens/overview).

- **WindowSize.Compact**: Most phones in portrait mode. (0-599 dp range)
- **WindowSize.Medium**: Most foldables and tablets in portrait mode. (600-839 dp range)
- **WindowSize.Expanded**: Most tablets in landscape mode. (840+ dp range)

You can get an instance of the `WindowSize` class with `getWindowSize()` method on your **Activity** or **Fragment** as following below:

```kotlin
val windowSize: WindowSize = getWindowSize()
when (windowSize) {
    is WindowSize.Compact -> // the window size is compact.
    is WindowSize.Medium -> // the window size is medium.
    is WindowSize.Expanded -> // the window size is expanded.
}
```

### GlobalWindowSize

You can customize the pre-defined breakpoints, which used to `getWindowSize()` with `GlobalWindowSize` object class as following below:

```kotlin
GlobalWindowSize.compactWindowDpSize = 600
GlobalWindowSize.mediumWindowDpSize = 840
```

Also, you can fully customize a factory function of the `WindowSize` class as following below:

```kotlin
GlobalWindowSize.windowSizeFactory = { windowPixelSize ->
    when {
        windowPixelSize.width < 0 -> throw IllegalArgumentException("Can't be negative")
        windowPixelSize.width < 600.dp2Px() -> WindowSize.Compact(windowPixelSize)
        windowPixelSize.width < 840.dp2Px() -> WindowSize.Medium(windowPixelSize)
        else -> WindowSize.Expanded(windowPixelSize)
    }
}
```

## Posture

<p align="center">
    <a href="https://medium.com/androiddevelopers/unbundling-the-windowmanager-fa060adb3ce9">
  <img src="/preview/posture.png" />
    </a>
</p>

> Fold state: `FLAT` and `HALF-OPENED` from [Google](https://medium.com/androiddevelopers/unbundling-the-windowmanager-fa060adb3ce9).

Posture class represents device postures in the flexible display or a hinge between two physical display panels.

- **Posture.TableTop** - Device posture is in tabletop mode (half open with the hinge horizontal).
- **Posture.Book** - Device posture is in book mode (half open with the hinge vertical).
- **Posture.Normal** - Device posture is in normal mode.

You can observe the posture as a Kotlin Flow on you **Activity** or **Fragment** as following below:

```kotlin
lifecycleScope.launch(Dispatchers.Main) {
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        postureFlow.collect { posture ->
            when (posture) {
                Posture.Normal -> // posture is Normal
                is Posture.TableTop -> // posture is TableTop
                is Posture.Book -> // posture is Book
            }
        }
        windowLayoutInfo.collect(::onWindowLayoutInfoUpdated)
    }
}
```
> Note: Make sure your project includes [Coroutines](https://developer.android.com/kotlin/coroutines) and `androidx.lifecycle:lifecycle-runtime-ktx:2.4.0` dependencies.

## WindowLayoutInfo

[WindowLayoutInfo](https://developer.android.com/reference/androidx/window/layout/WindowLayoutInfo) contains the list of [DisplayFeature](https://developer.android.com/reference/androidx/window/layout/DisplayFeature)-s
located within the window. You can observe the `WindowLayoutInfo` as following below:

```kotlin
lifecycleScope.launch(Dispatchers.Main) {
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        windowLayoutInfo.collect { windowLayoutInfo ->
            // something stuff            
        }
    }
}
```

## FoldingFeature

[FoldingFeature](https://developer.android.com/reference/androidx/window/layout/FoldingFeature) that describes a fold in the flexible display or a hinge between two physical display panels.
You can utilize the extensions below to check folding states and device postures:

```kotlin
val foldingFeature = windowLayoutInfo.displayFeatures.findFoldingFeature()
val posture = foldingFeature?.toPosture()
val isTableTopPosture = foldingFeature?.isTableTopPosture
val isBookPosture = foldingFeature?.isBookPosture
val isHalfOpened = foldingFeature?.isHalfOpened
val isFlat = foldingFeature?.isFlat
val isVertical = foldingFeature?.isVertical
val isHorizontal = foldingFeature?.isHorizontal
```

## WindowInfoActivity

Butterfly supports `WindowInfoActivity`, which tracks window configurations and update the [WindowLayoutInfo](https://developer.android.com/reference/androidx/window/layout/WindowLayoutInfo).
It has a default `windowSize` property and `onWindowLayoutInfoUpdated` abstract method as in the example below:

```kotlin
class MainActivity : WindowInfoActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // windowSize property will be initialized lazily.
        when (windowSize) {
            is WindowSize.Compact -> 
            ...
        }
    }

    override fun onWindowLayoutInfoUpdated(windowLayoutInfo: WindowLayoutInfo) {
        val foldingFeature = windowLayoutInfo.displayFeatures.findFoldingFeature() ?: return
        when (val posture = foldingFeature.toPosture()) {
            Posture.Normal -> Log.d(tag, "[Posture.Normal] ${posture.size}")
            is Posture.TableTop -> Log.d(tag, "[Posture.TableTop] ${posture.size}")
            ...
        }
    }
}
```

The pre-defined `windowSize` property will be initialized lazily and the `onWindowLayoutInfoUpdated` will be updated
when the `WindowLayoutInfo` configuration changed. As the same concept, you can extend `WindowInfoFragment` for your **Fragment**.

<img align="right" width="15%" src="https://user-images.githubusercontent.com/24237865/149444862-961adb83-da2a-4179-9c27-37edb2f982f4.png">

## Butterfly for Jetpack Compose

[![Maven Central](https://img.shields.io/maven-central/v/io.getstream/butterfly-compose.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.getstream%22%20AND%20a:%22butterfly-compose%22)

Butterfly supports Jetpack Compose to build adaptive and responsive UIs. First, add the dependency below to your **module's** `build.gradle` file.

```gradle
dependencies {
    implementation "io.getstream:butterfly-compose:1.0.0"
}
```

### WindowDpSize

WindowDpSize represents breakpoints, which are the screen size at which a layout will adapt to best fit content and conform to responsive layout requirements. 
Butterfly follows three breakpoints by [Material Design](https://m3.material.io/foundations/adaptive-design/large-screens/overview).

You can remember an instance of the `WindowDpSize` class with `rememberWindowDpSize()` method on your **Activity** or **Fragment** as following below:

```kotlin
val windowDpSize: WindowDpSize = rememberWindowDpSize()
when (windowDpSize) {
    is WindowSize.Compact -> MainScreenRegular()
    is WindowSize.Medium -> MainScreenMedium()
    is WindowSize.Expanded -> MainScreenExpanded()
}
```

> Note: Likewise the `WindowSize`, you can also customize the pre-defined breakpoints, which used to `rememberWindowDpSize` with the [GlobalWindowSize](https://github.com/GetStream/butterfly#globalwindowsize) object class.

## Posture

You can get a **State** of **Posture** to build adaptive and responsive UIs with the `postureState` extension on your **Activity** and **Fragment** as following below:

```kotlin
val postureState: State<Posture> = postureState
when (postureState.value) {
    Posture.Normal -> // posture is Normal
    is Posture.TableTop -> // posture is TableTop
    is Posture.Book -> // posture is Book
}
```

## WindowLayoutInfo

[WindowLayoutInfo](https://developer.android.com/reference/androidx/window/layout/WindowLayoutInfo) contains the list of [DisplayFeature](https://developer.android.com/reference/androidx/window/layout/DisplayFeature)-s
located within the window. You can get the **State** of the `WindowLayoutInfo` as following below:

```kotlin
val windowLayoutInfoState: State<WindowLayoutInfo> = windowLayoutInfoState
val foldingFeature = windowLayoutInfoState.value.displayFeatures.findFoldingFeature()
...
```

## CompositionLocal

You can pass instances of the `WindowDpSize` and `Posture` down through the Composition implicitly as following below:

```kotlin
CompositionLocalProvider(LocalWindowDpSize provides rememberWindowDpSize()) {
    val windowDpSize = LocalWindowDpSize.current
    ...
}

CompositionLocalProvider(LocalPosture provides postureState.value) {
    val posture = LocalPosture.current
    ...                
}
```

<img align="right" width="15%" src="https://user-images.githubusercontent.com/24237865/149445065-47c2506d-a738-4fb2-b4fb-eb6841b9e202.png" />

## Butterfly for LiveData Integration

[![Maven Central](https://img.shields.io/maven-central/v/io.getstream/butterfly-livedata.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.getstream%22%20AND%20a:%22butterfly-livedata%22)

Butterfly supports **LiveData** integration to let observing layout changes as **LiveData**. First, add the dependency below to your **module's** `build.gradle` file.

```gradle
dependencies {
    implementation "io.getstream:butterfly-livedata:1.0.0"
}
```

You can observe **LiveData** of `Posture` and `WindowLayoutInfo` on your **Activity** and **Fragment** as in the following example below:

```kotlin
postureLiveData().observe(this) { posture ->
    // do something
}

windowLayoutInfoLiveData().observe(this) { windowLayoutInfo ->
    // do something
}
```

 <a href="https://getstream.io/tutorials/android-chat/">
<img src="https://user-images.githubusercontent.com/24237865/138428440-b92e5fb7-89f8-41aa-96b1-71a5486c5849.png" align="right" width="12%"/></a>

## Find this library useful? ❤️

Support it by joining __[stargazers](https://github.com/getStream/butterfly/stargazers)__ for this repository. ⭐️ <br>
Also, follow **[Stream](https://twitter.com/getstream_io)** on Twitter for our next creations!

# License
```xml
Copyright 2022 Stream.IO, Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
