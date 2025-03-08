package jp.co.yumemi.android.codecheck.buildlogic.configure.hilt

import jp.co.yumemi.android.codecheck.buildlogic.extension.implementation
import jp.co.yumemi.android.codecheck.buildlogic.extension.ksp
import jp.co.yumemi.android.codecheck.buildlogic.extension.library
import jp.co.yumemi.android.codecheck.buildlogic.extension.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureHilt() {
    with(pluginManager) {
        apply("com.google.dagger.hilt.android")
        apply("kotlin-kapt")
    }
    dependencies {
        implementation(libs.library("hilt-android"))
        ksp(libs.library("hilt-compiler"))
    }
}