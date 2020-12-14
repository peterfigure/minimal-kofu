package com.peterfigure.config

import com.peterfigure.domain.persistence.ProcessStateTypeReadingConverter
import com.peterfigure.domain.persistence.ProcessStateTypeWritingConverter
import com.peterfigure.repository.ContractLifecycleRepository
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import mu.KLogging
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryBuilder
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsBuilderCustomizer
import org.springframework.boot.autoconfigure.r2dbc.EmbeddedDatabaseConnection
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.support.beans
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext
import org.springframework.data.relational.core.mapping.NamingStrategy
import org.springframework.fu.kofu.ConfigurationDsl
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.dataR2dbc
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator

object R2DBCConfigurationDSL: KLogging() {
    val customConverters = listOf(
        ProcessStateTypeReadingConverter(),
        ProcessStateTypeWritingConverter()
    )

    private fun connectionFactoryOptionsCustomizers(databaseConfig: DatabaseConfig): List<ConnectionFactoryOptionsBuilderCustomizer> =
        listOf(
            ConnectionFactoryOptionsBuilderCustomizer {
                it.option(ConnectionFactoryOptions.DRIVER, "pool")
                    .option(ConnectionFactoryOptions.PROTOCOL, "postgresql") // driver identifier, PROTOCOL is delegated as DRIVER by the pool.
                    .option(ConnectionFactoryOptions.USER, databaseConfig.username)
                    .option(ConnectionFactoryOptions.PASSWORD, databaseConfig.password)
                    .option(ConnectionFactoryOptions.DATABASE, databaseConfig.databaseName)
                    .option(io.r2dbc.spi.Option.valueOf("maxSize"), databaseConfig.connectionPoolSize)
                    .build()
            }
        )

    fun databaseConfiguration(databaseConfig: DatabaseConfig): ConfigurationDsl {
        return configuration {
            // let spring generate this repository from interface
//            beans {
//                bean<ContractLifecycleRepository> {
//                    ContractLifecycleRepository(ref())
//                }
//            }
            dataR2dbc {
                r2dbc {
                    url = databaseConfig.url
                    transactional = true // creates transactional operator
                    optionsCustomizers = connectionFactoryOptionsCustomizers(databaseConfig)
                    username = databaseConfig.username
                    password = databaseConfig.password
                }
            }
        }
    }

    // we are manually replicating R2dbcDataInitializer here since we cannot use the R2DBC DSL with generated repositories yet
    val mybeans = beans {

        bean<ConnectionFactory> {
            val databaseConfig = ref<DatabaseConfig>()

            val properties = R2dbcProperties().apply {
                name = databaseConfig.databaseName
                isGenerateUniqueName = false
                url = databaseConfig.url
                username = databaseConfig.username
                password = databaseConfig.password
            }

            ConnectionFactoryBuilder.of(properties) { EmbeddedDatabaseConnection.NONE } // TODO fix .. need a reference to classloader?
                .configure { options: ConnectionFactoryOptions.Builder? ->
                    for (optionsCustomizer in connectionFactoryOptionsCustomizers(databaseConfig)) {
                        optionsCustomizer.customize(options)
                    }
                }
                .build()
        }

        bean<DatabaseClient> {
            DatabaseClient.builder().connectionFactory(ref<ConnectionFactory>()).build()
        }

        bean<TransactionalOperator> {
            val reactiveTransactionManager = R2dbcTransactionManager(ref<ConnectionFactory>())
            TransactionalOperator.create(reactiveTransactionManager)
        }

        // unfortunately have to make this a bean
        bean<R2dbcDataAutoConfiguration> {
            R2dbcDataAutoConfiguration(ref())
        }

        bean<R2dbcEntityTemplate>("r2dbcEntityTemplate") {
            ref<R2dbcDataAutoConfiguration>().r2dbcEntityTemplate(ref())
        }
        bean<R2dbcMappingContext> {
            ref<R2dbcDataAutoConfiguration>().r2dbcMappingContext(provider<NamingStrategy>(), ref())
        }
        bean<MappingR2dbcConverter> {
            ref<R2dbcDataAutoConfiguration>().r2dbcConverter(ref(), ref())
        }
    }
}
