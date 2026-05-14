package com.example.nimmaguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "sessions",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["guruId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val guruId: Long,
    val title: String,
    val subject: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val maxStudents: Int
)
