pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "android-engineer-codecheck"
include("app")
include("data")
includeChildModules("feature")

fun includeChildModules(parentName: String) {
    val buildGradleName = "build.gradle.kts"
    with(File(rootProject.projectDir, parentName)) {
        listFiles()
            ?.asSequence()
            ?.filter { it.isDirectory }
            ?.filter { File(it, buildGradleName).exists() }
            ?.forEach {
                include("$parentName:${it.name}")
            }
    }
}

// project("hoge") -> projects.hoge
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")