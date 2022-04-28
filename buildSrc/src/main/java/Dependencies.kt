
object Versions {
    const val kotlin = "1.6.21"

    const val android_x = "1.4.1"

    const val circleindicator = "2.1.6"

    const val junit = "4.13.2"
    const val mockito_core = "4.5.1"
    const val mockito_kotlin = "4.0.0"
    const val truth = "1.1.3"
    const val robolectric = "4.7.3"

    const val gradle_android = "7.1.3"
    const val jacoco = "0.8.8"

    const val min_sdk = 14
    const val target_sdk = 31
    const val compile_sdk = 31

    const val lemniscate_version_code = 204
    const val lemniscate_version_name = "2.0.4"

    const val sample_version_code = 133
    const val sample_version_name = "1.3.3"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    const val appcompat = "androidx.appcompat:appcompat:${Versions.android_x}"

    const val circleindicator = "me.relex:circleindicator:${Versions.circleindicator}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"
    const val mockito_kotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito_kotlin}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_android}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}
