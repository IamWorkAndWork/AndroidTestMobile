package app.practice.question5.domain

class Page2UseCase(private val repo: UserRepository) {

    suspend fun getAllUsers(): List<User> {
        return repo.getAllUser()
    }

    suspend fun deleteUser(user: User): Int {
        return repo.deleteUser(user)
    }
}