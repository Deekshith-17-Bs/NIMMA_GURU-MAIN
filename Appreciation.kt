package com.example.nimmaguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "appreciations",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["guruId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Appreciation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val guruId: Long,
    val message: String,
    val date: Long = System.currentTimeMillis()
)
