import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id(PluginIds.SpringBoot) version PluginVersions.SpringBoot
    kotlin(PluginIds.KotlinSpring) version PluginVersions.Kotlin
    kotlin(PluginIds.KotlinKapt) // must not have a version apparently
    kotlin(PluginIds.KotlinXSerialization) version PluginVersions.Kotlin
    id(PluginIds.Idea)
    id(PluginIds.SourceSets) version PluginVersions.SourceSets
}

testSets {
    create("integrationTest") {
        dirName = "integration-test"
    }
}

tasks {
    "check" {
        dependsOn("integrationTest")
    }
    "integrationTest" {
        mustRunAfter("test")
        outputs.upToDateWhen { false } // force full run every time
    }
}

dependencies {
    implementation.let {
        // Spring
        it(Libraries.SpringBootStarterActuator)
        it(Libraries.SpringBootStarterWebFlux) {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
        }
        it(Libraries.SpringBootStarterJetty)
        it(Libraries.SpringDataR2DBC)
        it(Libraries.SpringFuKofu)

        it(Libraries.ArrowCore)
        it(Libraries.ArrowSyntax)
        it(Libraries.Config4k)
        it(Libraries.FlywayCore)
        it(Libraries.KotlinLogging)
        it(Libraries.KotlinXCoRoutinesCore)
        it(Libraries.KotlinXCoRoutinesJDK8)
        it(Libraries.KotlinXCoRoutinesReactor)
        it(Libraries.KotlinXSerializationCore)
        it(Libraries.ProjectReactorCore)
        it(Libraries.ProjectReactorKafka)
        it(Libraries.ProjectReactorKotlinExtensions)
        it(Libraries.ProjectReactorNetty)
        it(Libraries.R2DBCPostgres)
        it(Libraries.R2DBCPool)
        it(Libraries.SLF4JAPI)
    }
    kapt(Libraries.ArrowMeta)
    runtimeOnly.let {
        it(Libraries.FlywayCore)
        it(Libraries.Postgres) // for running flyway when the app starts up
    }

    testImplementation.let {
        it(Libraries.SpringBootStarterTest) {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
            exclude(group = "org.mockito", module = "mockito-core")
        }
        it(Libraries.Kotest)
        it(Libraries.KotestAssertions)
        it(Libraries.KotestSpring)
    }

    integrationTestImplementation.let {
        it(Libraries.KotestTestContainers)
        it(Libraries.SpringBootTest)
        it(Libraries.SpringBootStarterTest) 
    }

    developmentOnly(Libraries.SpringBootDevTools)
}

springBoot {
}

tasks.bootJar {
    enabled = true
}
