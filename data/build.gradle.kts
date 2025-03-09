plugins {
    alias(libs.plugins.yumemi.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android.namespace = "jp.co.yumemi.android.codecheck.data"

dependencies {
    implementation(projects.domain)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.hilt.android)
    testImplementation(libs.hilt.android.testing)
    ksp(libs.hilt.compiler)
}
