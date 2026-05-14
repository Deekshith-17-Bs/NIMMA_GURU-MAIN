package com.example.nimmaguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "enrollments",
    foreignKeys = [
        ForeignKey(
            entity = Session::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Enrollment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val studentId: Long,
    val status: String // "Joined", "Cancelled"
)
