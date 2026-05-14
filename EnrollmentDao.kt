package com.example.nimmaguru.data.local

import androidx.room.*
import com.example.nimmaguru.data.model.Enrollment
import com.example.nimmaguru.data.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface EnrollmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enroll(enrollment: Enrollment)

    @Update
    suspend fun updateEnrollment(enrollment: Enrollment)

    @Query("SELECT * FROM enrollments WHERE studentId = :studentId")
    fun getEnrollmentsByStudent(studentId: Long): Flow<List<Enrollment>>

    @Query("SELECT * FROM enrollments WHERE sessionId = :sessionId")
    fun getEnrollmentsBySession(sessionId: Long): Flow<List<Enrollment>>

    @Query("SELECT s.* FROM sessions s INNER JOIN enrollments e ON s.id = e.sessionId WHERE e.studentId = :studentId")
    fun getEnrolledSessions(studentId: Long): Flow<List<Session>>
}
