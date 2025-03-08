package jp.co.yumemi.android.codecheck.buildlogic.configure.agp

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import jp.co.yumemi.android.codecheck.buildlogic.extension.libs
import jp.co.yumemi.android.codecheck.buildlogic.extension.version
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal const val APPLICATION_ID = "jp.co.yumemi.android.codecheck"

fun Project.configureAgpDefault() {
    extensions.configure<BaseExtension> {
        (this as? CommonExtension<*, *, *, *, *, *>)?.run {
            compileSdk = libs.version("compileSdk").toInt()
        }

        defaultConfig {
            minSdk = libs.version("minSdk").toInt()
            targetSdk = libs.version("targetSdk").toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
}

fun Project.configureAgpApplicationCertainSetting() {
    extensions.configure<BaseAppModuleExtension> {
        defaultConfig {
            applicationId = APPLICATION_ID
            versionCode = libs.version("versionCode").toInt()
            versionName = with(libs) {
                listOf(
                    version("versionMajorName"),
                    version("versionMinorName"),
                    version("versionPatchName"),
                ).joinToString(".")
            }
        }
        lint {
            checkDependencies = true
            checkGeneratedSources = true
        }
        buildFeatures {
            viewBinding = true
        }
    }
}
