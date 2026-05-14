package com.example.nimmaguru.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class GuruWithProfile(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val profile: GuruProfile?
)
