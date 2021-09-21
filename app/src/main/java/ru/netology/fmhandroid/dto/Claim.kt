package ru.netology.fmhandroid.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize
import ru.netology.fmhandroid.entity.UserEntity

@Parcelize
data class Claim(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val creatorId: Int? = null,
    var executorId: Int = 0,
    val createDate: Long? = null,
    val planExecuteDate: Long? = null,
    val factExecuteDate: Long? = null,
    var status: Status? = null,
    val deleted: Boolean = false,
) : Parcelable {

    enum class Status {
        CANCELLED,
        EXECUTED,
        IN_PROGRESS,
        OPEN
    }
}

@kotlinx.parcelize.Parcelize
data class ClaimWithCreatorAndExecutor(
    @Embedded
    val claim: Claim,

    @Relation(
        entity = UserEntity::class,
        parentColumn = "creatorId",
        entityColumn = "id"
    )
    val creator: User,

    @Relation(
        entity = UserEntity::class,
        parentColumn = "executorId",
        entityColumn = "id"
    )
    var executor: User?

) : Parcelable