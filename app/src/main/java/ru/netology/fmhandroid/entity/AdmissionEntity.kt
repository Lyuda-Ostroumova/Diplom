package ru.netology.fmhandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.fmhandroid.dto.Admission

@Entity(tableName = "AdmissionEntity")
data class AdmissionEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "dateIn")
        val dateIn: String,
        @ColumnInfo(name = "dateOut")
        val dateOut: String,
        @ColumnInfo(name = "factIn")
        val factIn: String,
        @ColumnInfo(name = "factOut")
        val factOut: String,
        @ColumnInfo(name = "deleted")
        val deleted: Boolean = false,
        @ColumnInfo(name = "patientId")
        val patientId: Long,
) {
    fun toDto() = Admission(
            id,
            dateIn,
            dateOut,
            factIn,
            factOut,
            deleted,
            patientId
    )
}

    fun List<AdmissionEntity>.toDto(): List<Admission> = map(AdmissionEntity::toDto)
    fun List<Admission>.toEntity(): List<AdmissionEntity> = map(Admission::toEntity)
    fun Admission.toEntity() = AdmissionEntity(
            id,
            dateIn,
            dateOut,
            factIn,
            factOut,
            deleted,
            patientId
    )