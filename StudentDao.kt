package com.example.nimmaguru.data.local

import androidx.room.*
import com.example.nimmaguru.data.model.StudentProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: StudentProfile)

    @Query("SELECT * FROM student_profiles WHERE userId = :userId")
    fun getProfileByUserId(userId: Long): Flow<StudentProfile?>
}
