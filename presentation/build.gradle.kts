plugins {
    alias(libs.plugins.yumemi.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android.namespace = "jp.co.yumemi.android.codecheck.presentation"

dependencies {
    api(projects.domain)
    androidTestImplementation(projects.testing)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // TODO: Jetpack Composeに移行完了したら削除する。
    implementation("com.google.android.material:material:1.4.0")

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.espresso.core)
}
