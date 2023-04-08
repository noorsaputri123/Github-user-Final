package com.rie.githubuser.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rie.githubuser.data.local.entity.UserEntity
import com.rie.githubuser.data.remote.repository.UserRepository
import com.rie.githubuser.data.remote.retrofit.ApiConfig
import com.rie.githubuser.util.Event
import com.rie.githubuser.data.remote.response.ItemsSearch
import com.rie.githubuser.data.remote.response.ResponseSearchDetail
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//Noor Saputri
class DetailViewModel(private val usersRepository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _followers = MutableLiveData<List<ItemsSearch>>()
    val followers: LiveData<List<ItemsSearch>> = _followers

    private val _following = MutableLiveData<List<ItemsSearch>>()
    val following: LiveData<List<ItemsSearch>> = _following



    fun getUser(username: String) = usersRepository.getUser(username)

    fun saveFavorite(user: UserEntity) {
        viewModelScope.launch {
            usersRepository.setUsersFavourite(user, true)
        }
    }

    fun deleteFavorite(user: UserEntity) {
        viewModelScope.launch {
            usersRepository.setUsersFavourite(user, false)
        }
    }

    fun getFavoriteUsers() = usersRepository.getFavouriteUsers()

    fun deleteAll(){
        viewModelScope.launch {
            usersRepository.deleteAll()
        }
    }

    fun getFollowers(username: String?) {

        val client = ApiConfig.getApiService().getFollowers(username)
        _isLoading.value = true
        client.enqueue(object : Callback<List<ItemsSearch>> {
            override fun onResponse(
                call: Call<List<ItemsSearch>>,
                response: Response<List<ItemsSearch>>
            ) {

                if (response.isSuccessful) {
                    _isLoading.value = false
                    _followers.value = response.body()
                } else {
                    _toastText.value = Event("Tidak ada data yang ditampilkan!")
                }
            }
            override fun onFailure(call: Call<List<ItemsSearch>>, t: Throwable) {
                _isLoading.value = false
                Log.d("Follow", t.message.toString())
                _toastText.value = Event("onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String?) {

        val client = ApiConfig.getApiService().getFollowing(username)
        _isLoading.value = true
        client.enqueue(object : Callback<List<ItemsSearch>> {

            override fun onResponse(
                call: Call<List<ItemsSearch>>,
                response: Response<List<ItemsSearch>>
            ) {

                if (response.isSuccessful) {
                    _isLoading.value = false
                    _following.value = response.body()
                } else {
                    _toastText.value = Event("Tidak ada data yang ditampilkan!")
                }
            }
            override fun onFailure(call: Call<List<ItemsSearch>>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event("onFailure: ${t.message.toString()}")
            }
        })
    }
}