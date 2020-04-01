
object Versions {
    const val kotlin = "1.3.71"

    const val android_x = "1.1.0"

    const val circleindicator = "2.1.4"

    const val junit = "4.13"
    const val mockito_core = "3.3.3"
    const val mockito_kotlin = "2.2.0"
    const val truth = "1.0.1"
    const val robolectric = "4.3.1"

    const val gradle_android = "3.6.1"
    const val jacoco = "0.8.5"

    const val min_sdk = 14
    const val target_sdk = 29
    const val compile_sdk = 29
    const val build_tools = "29.0.3"

    const val lemniscate_version_code = 203
    const val lemniscate_version_name = "2.0.3"

    const val sample_version_code = 132
    const val sample_version_name = "1.3.2"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val appcompat = "androidx.appcompat:appcompat:${Versions.android_x}"

    const val circleindicator = "me.relex:circleindicator:${Versions.circleindicator}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_android}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}
