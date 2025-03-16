package jp.co.yumemi.android.codecheck.buildlogic.configure.detekt

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import jp.co.yumemi.android.codecheck.buildlogic.extension.detektPlugins
import jp.co.yumemi.android.codecheck.buildlogic.extension.library
import jp.co.yumemi.android.codecheck.buildlogic.extension.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

fun Project.configureDetekt() {
    with(pluginManager) {
        apply("io.gitlab.arturbosch.detekt")
    }

    (extensions.getByName("detekt") as? DetektExtension)?.apply {
        buildUponDefaultConfig = true
        config.setFrom("$rootDir/detekt.yml")
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "17"
    }

    tasks.withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget = "17"
    }

    dependencies {
        detektPlugins(libs.library("detekt-compose-rules"))
        detektPlugins(libs.library("detekt-formatting-rules"))
    }
}
