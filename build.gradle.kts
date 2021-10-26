import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application

    kotlin("jvm")
    kotlin("plugin.serialization")

    id("com.github.jakemarsden.git-hooks")
    id("com.github.johnrengelman.shadow")
    id("io.gitlab.arturbosch.detekt")
}

group = "io.github.jamalam360"
version = "0.1.2"

repositories {
    maven {
        name = "Kotlin Discord"
        url = uri("https://maven.kotlindiscord.com/repository/maven-public/")
    }
}

dependencies {
    detektPlugins(libs.detekt)

    implementation(libs.kord.extensions)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kx.ser)

    // Logging dependencies
    implementation(libs.groovy)
    implementation(libs.logback)
    implementation(libs.logging)
    implementation(libs.kmongo)
}

application {
    // This is deprecated, but the Shadow plugin requires it
    mainClassName = "io.github.jamalam360.BotKt"
}

gitHooks {
    setHooks(
        mapOf("pre-commit" to "detekt")
    )
}

tasks.build {
    doFirst {
        println("-----------------------------------------------------------------------------------")
        println("-------------------------------------- Stop! --------------------------------------")
        println("------------------------- Are you building a new version? -------------------------")
        println("----------------- Did you update the version in build.gradle.kts? -----------------")
        println("---------------------- Did you update the version in Bot.kt? ----------------------")
        println("-----------------------------------------------------------------------------------")
    }
}

tasks.detekt {
    onlyIf {
        false
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"

    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "io.github.jamalam360.BotKt"
        )
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

detekt {
    buildUponDefaultConfig = true
    config = rootProject.files("detekt.yml")
    autoCorrect = true
}
