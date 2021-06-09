package com.example.myweather.network

import com.example.myweather.view.model.MyWeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather/")
    fun getWeatherInfo(@Query("lat") latitude : Double?, @Query("lon") longitude : Double?
                       ,@Query("appid") appid : String?): Call<MyWeatherModel?>?
}