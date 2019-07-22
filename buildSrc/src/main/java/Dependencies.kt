
object Versions {
    const val kotlin = "1.3.41"

    const val android_x = "1.0.2"

    const val circleindicator = "2.1.2"

    const val junit = "4.12"
    const val mockito_core = "2.28.2"
    const val mockito_kotlin = "2.1.0"
    const val truth = "0.46"
    const val robolectric = "4.2.1"

    const val gradle_android = "3.4.2"
    const val jacoco = "0.8.4"

    const val min_sdk = 14
    const val target_sdk = 28
    const val compile_sdk = 28
    const val build_tools = "28.0.3"

    const val lemniscate_version_code = 201
    const val lemniscate_version_name = "2.0.1"

    const val sample_version_code = 130
    const val sample_version_name = "1.3.0"
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
