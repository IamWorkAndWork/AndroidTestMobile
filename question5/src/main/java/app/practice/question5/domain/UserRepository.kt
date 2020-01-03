package app.practice.question5.domain

interface UserRepository {
    suspend fun insert(user: User): Long

    suspend fun editUser(user: User): Int

    suspend fun getAllUser(): List<User>

    suspend fun deleteUser(user: User): Int

    suspend fun getUserByID(id: Int): User

}