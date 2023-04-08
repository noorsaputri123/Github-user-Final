package com.rie.githubuser.data.di

import android.content.Context
import com.rie.githubuser.data.local.room.UserDatabase
import com.rie.githubuser.data.remote.repository.UserRepository
import com.rie.githubuser.data.remote.retrofit.ApiConfig
//Noor Saputri
object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}