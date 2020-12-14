package com.peterfigure.config

import arrow.core.extensions.list.foldable.firstOption
import arrow.core.getOrElse
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import mu.KLogging
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

object ExternalDependencyConfigurationDSL: KLogging() {

    fun beans(config: Config?) =
        beans {
            logger.info("Creating External Dependency beans using Functional DSL")

            val actualConfig = config ?: ConfigManager.loadConfig(env.activeProfiles.toList())

            /*
                  ---------------------------- [ CONFIGURATION ]------
                 */
            bean<DatabaseConfig> {
                actualConfig.extract<DatabaseConfig>("database")
            }
        }
}

object ConfigManager: KLogging() {
    fun loadConfig(applicationContext: GenericApplicationContext): Config {
        return loadConfig(applicationContext.environment.activeProfiles.toList())
    }
    fun loadConfig(activeProfiles: List<String>): Config {
        val configFileName = activeProfiles.toList().firstOption().map { "application-$it.conf" }.getOrElse { "application-development.conf" }
        logger.info("Loading configuration from $configFileName")
        return ConfigFactory.load(configFileName)
    }
}

data class DatabaseConfig(
    val url: String,
    val username: String,
    val password: String,
    val databaseName: String,
    val schema: String,
    val connectionPoolSize: Int
)