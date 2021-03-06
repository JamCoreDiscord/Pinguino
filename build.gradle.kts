import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application

    kotlin("jvm")
    kotlin("plugin.serialization")

    id("com.github.jakemarsden.git-hooks")
    id("com.github.johnrengelman.shadow")
    id("io.gitlab.arturbosch.detekt")
    id("org.cadixdev.licenser")
}

group = "io.github.jamalam"
version = "0.9.1"

repositories {
    maven {
        name = "Kord Snapshots"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }

    maven {
        name = "Kotlin Discord"
        url = uri("https://maven.kotlindiscord.com/repository/maven-public/")
    }
}

dependencies {
    detektPlugins(libs.detekt)

    implementation(libs.kordex)
    implementation(libs.kordex.extra.common)
    implementation(libs.kordex.extra.phishing)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kx.ser)
    implementation(libs.kmongo)
    implementation(libs.kaml)

    // Logging dependencies
    implementation(libs.groovy)
    implementation(libs.logback)
    implementation(libs.logging)
}

application {
    // This is deprecated, but the Shadow plugin requires it
    @Suppress("DEPRECATION")
    mainClassName = "io.github.jamalam.pinguino.BotKt"
}

gitHooks {
    setHooks(
        mapOf("pre-commit" to "detekt")
    )
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"

    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "io.github.jamalam.pinguino.BotKt"
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

license {
    header.set(project.resources.text.fromFile(project.file("HEADER")))
}
