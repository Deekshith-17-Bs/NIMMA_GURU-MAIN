package com.example.nimmaguru.data.local

import androidx.room.*
import com.example.nimmaguru.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Long): Flow<User?>

    @Query("SELECT * FROM users WHERE role = 'GURU'")
    fun getAllGurusWithProfiles(): Flow<List<com.example.nimmaguru.data.model.GuruWithProfile>>
}
