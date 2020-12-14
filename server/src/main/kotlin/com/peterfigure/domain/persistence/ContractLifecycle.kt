package com.peterfigure.domain.persistence

import com.peterfigure.domain.persistence.ContractLifecycle.Companion.TableName
import org.springframework.core.convert.converter.Converter
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.Transient
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.UUID

@Table(TableName)
data class ContractLifecycle(
    @Id @Column("execution_id") val executionId: UUID,
    @Column("created_timestamp") val assetFirstSeenTimestamp: OffsetDateTime,
    @Column("last_updated_timestamp") val lastUpdatedTimestamp: OffsetDateTime,
    @Column("process_state") val processState: ProcessState,
    @Transient val new: Boolean
): Persistable<UUID> {
    companion object {
        const val TableName = "contract_lifecycle"
    }

    @PersistenceConstructor
    constructor(assetId: UUID, assetFirstSeenTimestamp: OffsetDateTime, lastUpdatedTimestamp: OffsetDateTime, processState: ProcessState):
        this(assetId, assetFirstSeenTimestamp, lastUpdatedTimestamp, processState, false)

    override fun getId(): UUID = executionId
    override fun isNew(): Boolean = new

    fun updateProcessState(
        processState: ProcessState = ProcessState.NORMAL,
        lastUpdatedTimestamp: OffsetDateTime = OffsetDateTime.now()): ContractLifecycle {
        return this.copy(processState = processState, lastUpdatedTimestamp = lastUpdatedTimestamp)
    }
}

enum class ProcessState() {
    UNKNOWN,
    NORMAL,
    SERIALIZATION_ERROR,
    DATA_GATHER_VALIDATION_FAILURE;
}

@ReadingConverter
class ProcessStateTypeReadingConverter : Converter<String, ProcessState> {
    override fun convert(source: String): ProcessState {
        return ProcessState.valueOf(source.toUpperCase())
    }
}

@WritingConverter
class ProcessStateTypeWritingConverter : Converter<ProcessState, String> {
    override fun convert(source: ProcessState): String {
        return source.name.toLowerCase()
    }
}