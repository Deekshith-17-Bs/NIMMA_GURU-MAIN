package com.example.nimmaguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "student_profiles",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class StudentProfile(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val classGrade: String,
    val subjectsNeeded: String,
    val schoolName: String
)
