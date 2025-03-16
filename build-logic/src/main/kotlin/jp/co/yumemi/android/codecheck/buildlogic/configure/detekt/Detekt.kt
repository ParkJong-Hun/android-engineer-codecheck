package jp.co.yumemi.android.codecheck.buildlogic.configure.detekt

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

fun Project.configureDetekt() {
    with(pluginManager) {
        apply("io.gitlab.arturbosch.detekt")
    }

    (extensions.getByName("detekt") as? DetektExtension)?.apply {
        buildUponDefaultConfig = true
        config.setFrom("$projectDir/detekt.yml")
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "17"
    }

    tasks.withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget = "17"
    }
}
