package com.example.myweather.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.repo.WeatherRepository
import com.example.myweather.view.model.MyWeatherModel

class WeatherViewModel(context: LifecycleOwner) : ViewModel() {
    var mContext = context

    private  var mutableLiveData: MutableLiveData<MyWeatherModel>? = null
    private var weatherRepository: WeatherRepository? = null

    fun init(latidude : Double, longitude : Double) {
        if (mutableLiveData != null) {
            return
        }
        weatherRepository = WeatherRepository.instance

        mutableLiveData = WeatherRepository().getWeatherInfo(mContext,latidude,
                longitude, "4669e6bc0d726ca494b23c34c9fbf72d")
    }

    fun getWeatherInfo(): LiveData<MyWeatherModel?>? {
        return mutableLiveData
    }
}



