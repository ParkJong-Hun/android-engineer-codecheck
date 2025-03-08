plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(gradleApi())
    implementation(libs.gradle.agp)
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.detekt)
    implementation(libs.gradle.ksp)
}

enum class ConventionPlugins(val id: String, val implementationClass: String) {
    YumemiApplicationPlugin(
        "jp.co.yumemi.android.codecheck.application",
        "jp.co.yumemi.android.codecheck.buildlogic.plugin.YumemiApplicationPlujgin"
    ),
    YumemiLibraryPlugin(
        "jp.co.yumemi.android.codecheck.library",
        "jp.co.yumemi.android.codecheck.buildlogic.plugin.YumemiLibraryPlugin"
    )
}

gradlePlugin {
    plugins {
        ConventionPlugins.values().forEach {
            register(it.id) {
                id = it.id
                implementationClass = it.implementationClass
            }
        }
    }
}
