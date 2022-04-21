package io.getstream.butterfly

object Versions {
    internal const val ANDROID_GRADLE_PLUGIN = "7.1.2"
    internal const val ANDROID_GRADLE_SPOTLESS = "6.2.1"
    internal const val GRADLE_NEXUS_PUBLISH_PLUGIN = "1.1.0"
    internal const val KOTLIN = "1.6.10"
    internal const val KOTLIN_GRADLE_DOKKA = "1.6.10"
    internal const val KOTLIN_BINARY_VALIDATOR = "0.8.0"
    internal const val KOTLIN_COROUTINE = "1.5.2"

    internal const val MATERIAL = "1.4.0"
    internal const val ANDROIDX_WINDOW = "1.1.0-alpha01"
    internal const val ANDROIDX_APPCOMPAT = "1.4.0"
    internal const val ANDROIDX_CORE = "1.7.0"
    internal const val ANDROIDX_LIFECYCLE = "2.4.0"
    internal const val AndroidX_LIVEDATA = "2.4.0"

    internal const val COMPOSE = "1.1.1"
    internal const val COMPOSE_ACTIVITY = "1.4.0"
    internal const val COMPOSE_MATERIAL3 = "1.0.0-alpha02"

    internal const val STREAM_SDK = "5.0.3"
}

object Dependencies {
    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    const val gradleNexusPublishPlugin =
        "io.github.gradle-nexus:publish-plugin:${Versions.GRADLE_NEXUS_PUBLISH_PLUGIN}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val spotlessGradlePlugin =
        "com.diffplug.spotless:spotless-plugin-gradle:${Versions.ANDROID_GRADLE_SPOTLESS}"
    const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.KOTLIN_GRADLE_DOKKA}"
    const val kotlinBinaryValidator =
        "org.jetbrains.kotlinx:binary-compatibility-validator:${Versions.KOTLIN_BINARY_VALIDATOR}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINE}"

    const val material = "com.google.android.material:material:${Versions.MATERIAL}"
    const val androidxWindowManager = "androidx.window:window:${Versions.ANDROIDX_WINDOW}"
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE}"
    const val androidxLifecycle =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}"
    const val androidxLiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX_LIVEDATA}"

    const val composeUI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.COMPOSE}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.COMPOSE}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY}"
    const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.COMPOSE_MATERIAL3}"

    const val streamChatSDK = "io.getstream:stream-chat-android-compose:${Versions.STREAM_SDK}"
}
