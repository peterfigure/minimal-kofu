object PluginIds { // please keep this sorted in sections
    // Kotlin
    const val Kotlin = "kotlin"
    const val KotlinSpring = "plugin.spring"
    const val KotlinKapt = "kapt"
    const val KotlinXSerialization = "plugin.serialization"

    // 3rd Party
    const val Flyway = "org.flywaydb.flyway"
    const val Idea = "idea"
    const val SourceSets = "org.unbroken-dome.test-sets"
    const val SpringBoot = "org.springframework.boot"
}

object PluginVersions { // please keep this sorted in sections
    // Kotlin
    const val Kotlin = "1.4.20"

    // 3rd Party
    const val Flyway = "6.4.4"
    const val SourceSets = "3.0.1"
    const val SpringBoot = "2.4.0"
    const val SpringDependencyManagement = "1.0.10.RELEASE"
}

object Versions {
    // kotlin
    const val Kotlin = PluginVersions.Kotlin
    const val KotlinXCoroutines = "1.4.2"
    const val KotlinXSerializationCore = "1.0.1"

    // 3rd Party
    const val Arrow = "0.11.0"
    const val Config4k = "0.4.2"
    const val Flyway = PluginVersions.Flyway
    const val Kotest = "4.3.1"
    const val KotlinLogging = "1.7.9"
    const val Logback = "1.2.3"
    const val ProjectReactor = "3.4.1"
    const val ProjectReactorKafka = "1.3.1"
    const val ProjectReactorKotlinExtensions = "1.1.1"
    const val ProjectReactorNetty = "1.0.2"
    const val Postgres = "42.2.5"
    const val SpringBoot = PluginVersions.SpringBoot
    const val SpringDataR2DBC = "1.2.2"
    const val R2DBCPool = "0.8.5.RELEASE"
    const val R2DBCPostgres = "0.8.6.RELEASE"
    const val SpringDependencyManagement = PluginVersions.SpringDependencyManagement
    const val SpringFu = "0.5.0-SNAPSHOT"
    const val SLF4J = "1.7.30"
}

object Libraries {
    // Kotlin
    const val KotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val KotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val KotlinAllOpen = "org.jetbrains.kotlin:kotlin-allopen:${Versions.Kotlin}"
    const val KotlinXCoRoutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KotlinXCoroutines}"
    const val KotlinXCoRoutinesJDK8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.KotlinXCoroutines}"
    const val KotlinXCoRoutinesReactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.KotlinXCoroutines}"
    const val KotlinXCoRoutinesSLF4J = "org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${Versions.KotlinXCoroutines}"
    const val KotlinXSerializationCore = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.KotlinXSerializationCore}"

    // 3rd Party
    const val ArrowCore = "io.arrow-kt:arrow-core:${Versions.Arrow}"
    const val ArrowFx = "io.arrow-kt:arrow-fx:${Versions.Arrow}"
    const val ArrowSyntax = "io.arrow-kt:arrow-syntax:${Versions.Arrow}"
    const val ArrowMeta = "io.arrow-kt:arrow-meta:${Versions.Arrow}"
    const val Config4k = "io.github.config4k:config4k:${Versions.Config4k}" // also consider hoplite
    const val FlywayCore = "org.flywaydb:flyway-core:${Versions.Flyway}"

    const val Kotest = "io.kotest:kotest-runner-junit5-jvm:${Versions.Kotest}"
    const val KotestAssertions = "io.kotest:kotest-assertions-core-jvm:${Versions.Kotest}"
    const val KotestSpring = "io.kotest:kotest-extensions-spring:${Versions.Kotest}"
    const val KotestTestContainers = "io.kotest:kotest-extensions-testcontainers:${Versions.Kotest}"

    const val KotlinLogging = "io.github.microutils:kotlin-logging:${Versions.KotlinLogging}"
    const val LogbackClassic = "ch.qos.logback:logback-classic:${Versions.Logback}"
    const val LogbackCore = "ch.qos.logback:logback-core:${Versions.Logback}"
    const val Postgres = "org.postgresql:postgresql:${Versions.Postgres}"
    const val ProjectReactorCore = "io.projectreactor:reactor-core:${Versions.ProjectReactor}"
    const val ProjectReactorKafka = "io.projectreactor.kafka:reactor-kafka:${Versions.ProjectReactorKafka}"
    const val ProjectReactorKotlinExtensions = "io.projectreactor.kotlin:reactor-kotlin-extensions:${Versions.ProjectReactorKotlinExtensions}"
    const val ProjectReactorNetty = "io.projectreactor.netty:reactor-netty:${Versions.ProjectReactorNetty}"

    const val R2DBCPool = "io.r2dbc:r2dbc-pool:${Versions.R2DBCPool}"
    const val R2DBCPostgres = "io.r2dbc:r2dbc-postgresql:${Versions.R2DBCPostgres}"
    const val SLF4JAPI = "org.slf4j:slf4j-api:${Versions.SLF4J}"

    const val SpringBootDevTools = "org.springframework.boot:spring-boot-devtools:${Versions.SpringBoot}"
    const val SpringBootStarterActuator = "org.springframework.boot:spring-boot-starter-actuator:${Versions.SpringBoot}"
    const val SpringBootStarterJetty = "org.springframework.boot:spring-boot-starter-jetty:${Versions.SpringBoot}"
    const val SpringBootStarterWebFlux = "org.springframework.boot:spring-boot-starter-webflux:${Versions.SpringBoot}"
    const val SpringBootStarterTest = "org.springframework.boot:spring-boot-starter-test:${Versions.SpringBoot}"
    const val SpringBootTest = "org.springframework.boot:spring-boot-test:${Versions.SpringBoot}"
    const val SpringDataR2DBC = "org.springframework.data:spring-data-r2dbc:${Versions.SpringDataR2DBC}"
    const val SpringFuKofu = "org.springframework.fu:spring-fu-kofu:${Versions.SpringFu}"
}

// gradle configurations
const val api = "api"
const val implementation = "implementation"
const val runtimeOnly = "runtimeOnly"
const val testCompileOnly = "testCompileOnly"
const val testImplementation = "testImplementation"
const val testRuntimeOnly = "testRuntimeOnly"
const val developmentOnly = "developmentOnly"
const val integrationTestImplementation = "integrationTestImplementation"
const val integrationTestRuntimeOnly = "integrationTestRuntimeOnly"