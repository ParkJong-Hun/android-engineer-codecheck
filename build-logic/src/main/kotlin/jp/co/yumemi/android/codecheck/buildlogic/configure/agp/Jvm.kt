package jp.co.yumemi.android.codecheck.buildlogic.configure.agp

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

private val DefaultKotlinOptions: KotlinJvmCompilerOptions.() -> Unit = {
    jvmTarget.set(JvmTarget.JVM_17)
}

fun Project.configureAgpJvmCompatibility() {
    extensions.findByType(CommonExtension::class)?.apply {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    extensions.findByType(KotlinAndroidProjectExtension::class)?.apply {
        compilerOptions(DefaultKotlinOptions)
    }
}