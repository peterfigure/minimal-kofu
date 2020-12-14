import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version PluginVersions.Kotlin apply false // I'm just here for the grammar
}

allprojects {
    group = "com.peterfigure"

    repositories {
        maven("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot")
        mavenCentral()
        jcenter()
    }
}

subprojects {
    // https://kotlinlang.org/docs/reference/using-gradle.html#using-gradle-kotlin-dsl
    apply {
        plugin(PluginIds.Kotlin)
        plugin(PluginIds.Idea)
    }

    repositories {
        mavenLocal()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xinline-classes")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation.let {
            // Kotlin
            it(Libraries.KotlinReflect)
            it(Libraries.KotlinStdlibJdk8)
        }
    }
}
