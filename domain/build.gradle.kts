plugins {
    alias(libs.plugins.yumemi.library)
}

android.namespace = "jp.co.yumemi.android.codecheck.domain"

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)
}
