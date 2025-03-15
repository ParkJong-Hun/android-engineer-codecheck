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
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.espresso.core)
}
