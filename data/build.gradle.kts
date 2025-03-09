plugins {
    alias(libs.plugins.yumemi.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android.namespace = "jp.co.yumemi.android.codecheck.data"

dependencies {
    implementation(projects.domain)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
