// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.4.10")
    val compose_version by extra("1.0.0-alpha04")
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.agp)
        classpath(Plugins.kotlin)
        classpath(Plugins.hilt)
        classpath(Plugins.sqldelight)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version "3.26.1"
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint()
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://www.jitpack.io")
    }
}
