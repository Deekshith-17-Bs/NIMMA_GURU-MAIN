package com.example.nimmaguru.data.repository

import com.example.nimmaguru.data.local.*
import com.example.nimmaguru.data.model.*
import kotlinx.coroutines.flow.Flow

class NimmaGuruRepository(
    private val userDao: UserDao,
    private val guruDao: GuruDao,
    private val studentDao: StudentDao,
    private val sessionDao: SessionDao,
    private val enrollmentDao: EnrollmentDao,
    private val appreciationDao: AppreciationDao
) {
    // User operations
    suspend fun insertUser(user: User): Long = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
    fun getUserById(id: Long): Flow<User?> = userDao.getUserById(id)
    fun getAllGurus(): Flow<List<GuruWithProfile>> = userDao.getAllGurusWithProfiles()

    // Profile operations
    suspend fun insertGuruProfile(profile: GuruProfile) = guruDao.insertProfile(profile)
    fun getGuruProfile(userId: Long): Flow<GuruProfile?> = guruDao.getProfileByUserId(userId)
    suspend fun insertStudentProfile(profile: StudentProfile) = studentDao.insertProfile(profile)
    fun getStudentProfile(userId: Long): Flow<StudentProfile?> = studentDao.getProfileByUserId(userId)

    // Session operations
    suspend fun insertSession(session: Session) = sessionDao.insertSession(session)
    fun getAllSessions(): Flow<List<Session>> = sessionDao.getAllSessions()
    fun getSessionsByGuru(guruId: Long): Flow<List<Session>> = sessionDao.getSessionsByGuru(guruId)
    suspend fun getSessionById(sessionId: Long): Session? = sessionDao.getSessionById(sessionId)

    // Enrollment operations
    suspend fun enroll(enrollment: Enrollment) = enrollmentDao.enroll(enrollment)
    fun getEnrollmentsByStudent(studentId: Long): Flow<List<Enrollment>> = enrollmentDao.getEnrollmentsByStudent(studentId)
    fun getEnrolledSessions(studentId: Long): Flow<List<Session>> = enrollmentDao.getEnrolledSessions(studentId)

    // Appreciation operations
    suspend fun insertAppreciation(appreciation: Appreciation) = appreciationDao.insertAppreciation(appreciation)
    fun getAppreciationsForGuru(guruId: Long): Flow<List<Appreciation>> = appreciationDao.getAppreciationsForGuru(guruId)
    fun getAllAppreciations(): Flow<List<Appreciation>> = appreciationDao.getAllAppreciations()
}
