package com.rie.githubuser.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rie.githubuser.data.local.entity.UserEntity
import com.rie.githubuser.data.local.room.UserDao
import com.rie.githubuser.data.remote.retrofit.ApiService
import com.rie.githubuser.data.remote.Result
import androidx.lifecycle.map
//Noor Saputri
class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    fun getFavouriteUsers() : LiveData<List<UserEntity>>{
        return userDao.getNewsFavUser()
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
    }

    suspend fun setUsersFavourite(user: UserEntity, favouriteState: Boolean){
        user.isFavorite = favouriteState
        userDao.updateUser(user)
    }

    fun getUser(username: String): LiveData<Result<UserEntity>> = liveData{
        emit(Result.Loading)
        try {
            val user = apiService.getUserDetail(username)
            val isFavourite = userDao.isUserFav(user.login)
            userDao.delete(user.login)
            userDao.insertUser(UserEntity(
                user.login,
                user.name,
                user.avatarUrl,
                user.company,
                user.followers,
                user.following,
                user.publicRepos,
                user.location,
                isFavourite
            ))
        }catch (e : Exception){
            Log.d("UsersRepository", "getUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val isFavourite = userDao.isUser(username)
        if(isFavourite){
            val localData : LiveData<Result<UserEntity>> = userDao.getUser(username).map { Result.Success(it) }
            emitSource(localData)
        }else{
            emit(Result.Error("Not Found!"))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            UserDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, UserDao)
            }.also { instance = it }
    }
}