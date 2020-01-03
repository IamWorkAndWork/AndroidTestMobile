package app.practice.question5.model

import app.practice.question5.domain.User
import app.practice.question5.domain.UserRepository

class UserRepositoryImpl(private val dao: UserDAO, private val mapper: DBUserMapper) :
    UserRepository {

    override suspend fun insert(user: User): Long {
        return dao.insert(mapper.toDb(user))
    }

    override suspend fun editUser(user: User): Int {
        return dao.updateUser(mapper.toDb(user))
    }

    override suspend fun getAllUser(): List<User> {
        return dao.getAllUsers().map {
            mapper.fromDb(it)
        }
    }

    override suspend fun deleteUser(user: User): Int {
        return dao.deleteUser(mapper.toDb(user))
    }

    override suspend fun getUserByID(id: Int): User {
        return mapper.fromDb(dao.getUserByID(id))
    }


}