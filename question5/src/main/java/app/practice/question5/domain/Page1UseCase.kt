package app.practice.question5.domain

class Page1UseCase(private val repo: UserRepository) {

    suspend fun addUser(user: User): Long {
        return repo.insert(user)
    }

    suspend fun updateUser(user: User): Int {
        return repo.editUser(user)
    }

    suspend fun getAllUsers(): List<User> {
        return repo.getAllUser()
    }

    suspend fun deleteUser(user: User): Int {
        return repo.deleteUser(user)
    }

}