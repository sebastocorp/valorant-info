package com.gamertop.valorantinfo.screens.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gamertop.valorantinfo.api.service.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterViewModel: ViewModel() {
    private val retrofit: Retrofit = getCRetrofit()

    private val _character = MutableLiveData<CharacterResponse>()
    var character: LiveData<CharacterResponse> = _character

    private val _isCharacterLoading = MutableLiveData<Boolean>()
    val isCharacterLoading: LiveData<Boolean> = _isCharacterLoading

    suspend fun getCharacter(uuid: String) {
        _isCharacterLoading.value = true
        val response: Response<CharacterDataResponse> = this.retrofit.create(ApiService::class.java).getCharacter(uuid)
        if (response.isSuccessful) {
            val responseBody: CharacterDataResponse? = response.body()
            if (responseBody != null) {
                _character.value = responseBody.data
            }
        }
        _isCharacterLoading.value = false
    }

    private fun getCRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://valorant-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}