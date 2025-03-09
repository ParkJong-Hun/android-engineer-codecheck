package jp.co.yumemi.android.codecheck.buildlogic.plugin

import jp.co.yumemi.android.codecheck.buildlogic.ConventionPlugin
import jp.co.yumemi.android.codecheck.buildlogic.configure.agp.configureAgpDefault
import jp.co.yumemi.android.codecheck.buildlogic.configure.agp.configureAgpJvmCompatibility
import jp.co.yumemi.android.codecheck.buildlogic.configure.detekt.configureDetekt
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
@ConventionPlugin("jp.co.yumemi.android.codecheck.library")
class YumemiLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
                apply("androidx.navigation.safeargs.kotlin")
            }
            configureAgpDefault()
            configureAgpJvmCompatibility()
            configureDetekt()
        }
    }
}
