package com.rie.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rie.githubuser.data.local.entity.UserEntity
//Noor Saputri
@Dao
interface UserDao {
    @Query("SELECT * FROM user where favorite = 1 order by login")
    fun getNewsFavUser(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user where login = :username AND favorite = 1)")
    suspend fun isUserFav(username: String?): Boolean

    @Query("SELECT EXISTS(SELECT * FROM user where login = :username)")
    suspend fun isUser(username: String?): Boolean

    @Query("DELETE FROM user WHERE login = :username")
    suspend fun delete(username: String?)

    @Query("SELECT * FROM user where login = :username")
    fun getUser(username: String): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user WHERE favorite = 0")
    suspend fun deleteAll()

}