plugins {
    alias(libs.plugins.yumemi.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android.namespace = "jp.co.yumemi.android.codecheck.feature.top"
android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.presentation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // TODO: Jetpack Composeに移行完了したら削除する。
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.kotlinx.coroutines.test)
}