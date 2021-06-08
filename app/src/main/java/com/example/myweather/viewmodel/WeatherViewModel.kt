package com.example.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.repo.WeatherRepository
import com.example.myweather.view.model.MyWeatherModel


class WeatherViewModel : ViewModel() {

  /*  mutableLiveData = WeatherRepository().getWeatherInfo(latitude: Double?, longitude: Double?, appid : String?): MutableLiveData<MyWeatherModel>? {
        return WeatherRepository.instance?.getWeatherInfo(latitude, longitude,appid)
    }*/

    private  var mutableLiveData: MutableLiveData<MyWeatherModel>? = null
    private var weatherRepository: WeatherRepository? = null

    fun init() {
        if (mutableLiveData != null) {
            return
        }
        weatherRepository = WeatherRepository.instance
        mutableLiveData = WeatherRepository().getWeatherInfo(21.1458,
                79.0882, "4669e6bc0d726ca494b23c34c9fbf72d")
    }

    fun getWeatherInfo(): LiveData<MyWeatherModel?>? {
        return mutableLiveData
    }


}



