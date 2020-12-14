package com.peterfigure

import com.peterfigure.config.ApplicationConfigurationDSL
import com.peterfigure.config.ConfigManager
import com.peterfigure.config.DatabaseConfig
import com.peterfigure.config.ExternalDependencyConfigurationDSL
import com.peterfigure.config.R2DBCConfigurationDSL
import io.github.config4k.extract
import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import java.util.Properties
import java.util.TimeZone
import kotlin.time.ExperimentalTime

@SpringBootApplication // == @Configuration @EnableAutoConfiguration @ComponentScan
class Application {
    companion object : KLogging() {
        val ActuatorProperties = Properties().apply {
            this["management.endpoints.enabled-by-default"] = false
            this["management.endpoint.health.enabled"] = true
            this["management.endpoint.info.enabled"] = true
            this["management.health.probes.enabled"] = true
            this["management.endpoint.health.show-components"] = "always"
            this["management.endpoint.health.show-details"] = "always"
        }
    }
}

class PeterFigureContextInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    companion object : KLogging()

    override fun initialize(applicationContext: GenericApplicationContext) {
        logger.info("Initializing ApplicationContext [$applicationContext] for PeterFigure")
        // TODO unfortunate we have to do this but no other way currently
        val config = ConfigManager.loadConfig(applicationContext)
        val databaseConfig = config.extract<DatabaseConfig>("database")

        ExternalDependencyConfigurationDSL.beans(null).initialize(applicationContext)
        ApplicationConfigurationDSL.beans.initialize(applicationContext)
        R2DBCConfigurationDSL.databaseConfiguration(databaseConfig).initialize(applicationContext)
    }
}

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    runApplication<Application>(*args) {
        setDefaultProperties(Application.ActuatorProperties)
        addInitializers(PeterFigureContextInitializer())
    }
}

// new DSL below but does not work yet with generate repositories, doesn't scan
//@OptIn(ExperimentalTime::class)
//fun main(args: Array<String>) {
//    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
//    val application = reactiveWebApplication {
//        val config = ConfigManager.loadConfig(env.activeProfiles.toList())
//        val databaseConfig = config.extract<DatabaseConfig>("database")
//
//        enable(configuration { ExternalDependencyConfigurationDSL.beans(config) })
//        enable(configuration { ApplicationConfigurationDSL.beans })
//        enable(R2DBCConfigurationDSL.databaseConfiguration(databaseConfig))
//        enable(
//            configuration {
//                webFlux {
//                    port = if (profiles.contains("test")) 8181 else 8080
//                    codecs {
//                        string()
//                    }
//                }
//            }
//        )
//        // TODO setDefaultProperties(Application.ActuatorProperties)
//    }
//    application.run()
//}