package com.gamertop.valorantinfo.screens.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gamertop.valorantinfo.api.service.ApiService
import com.gamertop.valorantinfo.api.service.CharacterListDataResponse
import com.gamertop.valorantinfo.api.service.CharacterListItemResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterListViewModel: ViewModel() {
    private val retrofit: Retrofit = getCLRetrofit()

    private val _characterList = MutableLiveData<List<CharacterListItemResponse>>()
    var characterList: LiveData<List<CharacterListItemResponse>> = _characterList

    private val _isCharacterListLoading = MutableLiveData<Boolean>()
    val isCharacterListLoading: LiveData<Boolean> = _isCharacterListLoading

    suspend fun getCharacterList() {
        _isCharacterListLoading.value = true
        val response: Response<CharacterListDataResponse> = this.retrofit.create(ApiService::class.java).getCharacterList()
        if (response.isSuccessful) {
            val responseBody: CharacterListDataResponse? = response.body()
            if (responseBody != null) {
                _characterList.value = responseBody.list
            }
        }
        _isCharacterListLoading.value = false
    }

    private fun getCLRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://valorant-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}