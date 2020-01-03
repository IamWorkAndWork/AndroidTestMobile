package app.practice.question5.model

import androidx.room.*

@Dao()
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity): Long

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(note: List<UserEntity>)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserByID(id: Int): UserEntity

    @Query("SELECT * FROM user ORDER BY id DESC")
    suspend fun getAllUsers(): List<UserEntity>

    @Update
    suspend fun updateUser(user: UserEntity): Int

    @Delete
    suspend fun deleteUser(user: UserEntity): Int
}