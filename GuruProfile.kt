package com.example.nimmaguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "guru_profiles",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GuruProfile(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val skills: String, // Comma separated: "Math, Science, Carpentry"
    val experienceYears: Int,
    val bio: String,
    val availableDays: String, // Comma separated: "Monday, Wednesday"
    val availableTimeSlots: String, // Comma separated: "10:00 AM - 12:00 PM"
    val locationDetails: String
)
