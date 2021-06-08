package com.example.myweather.repo

import androidx.lifecycle.MutableLiveData
import com.example.myweather.network.ApiInterface
import com.example.myweather.network.RetrofitInstance
import com.example.myweather.view.model.MyWeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    private val weatherApi: ApiInterface


    companion object {
        private val repository: WeatherRepository? = null
        val instance: WeatherRepository?
            get() = repository ?: WeatherRepository()
    }

    init {
        weatherApi = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    }

    fun getWeatherInfo(
        latitude: Double?,
        longitude: Double?,
        appid: String?
    ): MutableLiveData<MyWeatherModel> {
        var weatherData: MutableLiveData<MyWeatherModel> = MutableLiveData()
        weatherApi.getWeatherInfo(latitude, longitude, appid)
            ?.enqueue(object : Callback<MyWeatherModel?> {
                override fun onFailure(call: Call<MyWeatherModel?>, t: Throwable) {
                    //TODO("Not yet implemented")
                    weatherData.value = null
                }

                override fun onResponse(
                    call: Call<MyWeatherModel?>,
                    response: Response<MyWeatherModel?>
                ) {
                    //TODO("Not yet implemented")
                    if (response.isSuccessful) {
                        weatherData.value = response.body()
                    }
                }

            })
        return weatherData
    }
}
