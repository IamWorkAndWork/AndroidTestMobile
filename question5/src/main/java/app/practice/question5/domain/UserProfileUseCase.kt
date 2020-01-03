package app.practice.question5.domain

class UserProfileUseCase(private val repo: UserRepository) {

    suspend fun getUserByID(userID: Int): User {
        return repo.getUserByID(userID)
    }

    suspend fun deleteUser(user: User): Int {
        return repo.deleteUser(user)
    }
}