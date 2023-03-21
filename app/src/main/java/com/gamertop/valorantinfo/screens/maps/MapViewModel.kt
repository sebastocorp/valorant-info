package com.gamertop.valorantinfo.screens.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gamertop.valorantinfo.api.service.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapViewModel: ViewModel() {
    private val retrofit: Retrofit = getMRetrofit()

    private val _map = MutableLiveData<MapResponse>()
    var map: LiveData<MapResponse> = _map

    private val _isMapLoading = MutableLiveData<Boolean>()
    val isMapLoading: LiveData<Boolean> = _isMapLoading

    suspend fun getMap(uuid: String) {
        _isMapLoading.value = true
        val response: Response<MapDataResponse> = this.retrofit.create(ApiService::class.java).getMap(uuid)
        if (response.isSuccessful) {
            val responseBody: MapDataResponse? = response.body()
            if (responseBody != null) {
                _map.value = responseBody.data
            }
        }
        _isMapLoading.value = false
    }

    private fun getMRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://valorant-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}