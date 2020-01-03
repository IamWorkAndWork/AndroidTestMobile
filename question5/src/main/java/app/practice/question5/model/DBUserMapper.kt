package app.practice.question5.model

import app.practice.question5.domain.User

class DBUserMapper {

    fun fromDb(from: UserEntity) =
        User(from.id, from.firstName, from.lastName, from.birthdate, from.picture)

    fun toDb(from: User) =
        UserEntity(from.id, from.firstName, from.lastName, from.birthdate, from.picture)

}