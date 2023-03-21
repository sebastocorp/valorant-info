package com.gamertop.valorantinfo.screens.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gamertop.valorantinfo.api.service.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapListViewModel: ViewModel() {
    private val retrofit: Retrofit = getMLRetrofit()

    private val _mapList = MutableLiveData<List<MapListItemResponse>>()
    var mapList: LiveData<List<MapListItemResponse>> = _mapList

    private val _isMapListLoading = MutableLiveData<Boolean>()
    val isMapListLoading: LiveData<Boolean> = _isMapListLoading

    suspend fun getMapList() {
        _isMapListLoading.value = true
        val response: Response<MapListDataResponse> = this.retrofit.create(ApiService::class.java).getMapList()
        if (response.isSuccessful) {
            val responseBody: MapListDataResponse? = response.body()
            if (responseBody != null) {
                _mapList.value = responseBody.list
            }
        }
        _isMapListLoading.value = false
    }

    private fun getMLRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://valorant-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}