package com.peterfigure.repository

import com.peterfigure.domain.persistence.ContractLifecycle
import com.peterfigure.domain.persistence.ProcessState
import org.springframework.data.r2dbc.core.R2dbcEntityOperations
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.OffsetDateTime
import java.util.UUID

//class ContractLifecycleRepository(private val operations: R2dbcEntityOperations) {
//    fun findByExecutionID(executionID: UUID): Flux<ContractLifecycle> {
//        return operations.select(ContractLifecycle::class.java)
//            .from("contract_lifecycle")
//            .matching(query(where("execution_id").`is`(executionID))).all()
//    }
//}

interface ContractLifecycleRepository: ReactiveCrudRepository<ContractLifecycle, UUID>, MyReactiveRepository {

    @Modifying
    @Query("""
        update ${ContractLifecycle.TableName}
        set process_state = :processState, last_updated_timestamp = :lastUpdatedTimestamp
        where execution_id = :executionId""")
    fun updateState(
        executionId: UUID,
        processState: ProcessState = ProcessState.NORMAL,
        lastUpdatedTimestamp: OffsetDateTime = OffsetDateTime.now()): Mono<Boolean>
}
