package com.peterfigure.config

import com.peterfigure.config.R2DBCConfigurationDSL.customConverters
import io.r2dbc.spi.ConnectionFactory
import mu.KLogging
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.MigrationInfo
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer
import org.springframework.context.support.beans
import org.springframework.core.env.Environment
import org.springframework.data.convert.CustomConversions
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver
import java.util.ArrayList
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
object ApplicationConfigurationDSL: KLogging() {

    val beans =
        beans {
            logger.info("Creating Application Configuration beans using Functional DSL")
            /*
              ---------------------------- [ WEB ]------
             */

            /*
              ---------------------------- [ SERVICES ]------
             */
            // uncomment to see problem with two R2dbcCustomConversions beans
//            bean<R2dbcCustomConversions>() {
//                val dialect = DialectResolver.getDialect(ref<ConnectionFactory>())
//                val converters: MutableList<Any> = ArrayList(dialect.converters)
//                converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS)
//
//                R2dbcCustomConversions(CustomConversions.StoreConversions.of(dialect.simpleTypeHolder, converters), customConverters)
//            }

            /*
              ---------------------------- [ INFRASTRUCTURE ]------
             */
            bean {
                val databaseConfig = ref<DatabaseConfig>()
                Flyway
                    .configure()
                    .dataSource(databaseConfig.url.replace("r2dbc:", "jdbc:"), databaseConfig.username, databaseConfig.password)
                    .schemas(databaseConfig.schema)
                    .defaultSchema(databaseConfig.schema)
                    .load()
            }

            bean<FlywayMigrationInitializer> {
                fun MigrationInfo.statusIndication() = if (installedOn != null) "✓" else "+"

                val flyway = ref<Flyway>()

                FlywayMigrationInitializer(flyway).apply {
                    flyway.info().all().forEach {
                        logger.info("Flyway migration: ${it.statusIndication()} ${it.type} ${it.script}")
                    }
                }
            }
        }
}