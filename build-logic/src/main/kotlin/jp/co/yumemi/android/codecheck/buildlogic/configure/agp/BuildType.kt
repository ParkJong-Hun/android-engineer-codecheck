package jp.co.yumemi.android.codecheck.buildlogic.configure.agp

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureAgpBuildType() {
    extensions.configure<BaseExtension> {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles("proguard-rules.pro")
            }
        }
    }
}