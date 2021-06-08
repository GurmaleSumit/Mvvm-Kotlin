package com.example.myweather.view.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.R
import com.example.myweather.view.model.MyWeatherModel
import com.example.myweather.viewmodel.WeatherViewModel
import io.reactivex.Observable
import io.reactivex.observers.DisposableSingleObserver

class WeatherInfoActivity : AppCompatActivity() {

    lateinit var mTemperatureText : TextView
    lateinit var weatherViewModel : WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_info_activity)
        mTemperatureText = findViewById(R.id.temperature_tv);
        //mTemperatureText.setText("Hello Sumit! Welcome to kotlin")
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        loadWeatherInfo()

    }

    fun loadWeatherInfo() {
        if (isNetworkAvailbale()) {
            weatherViewModel.init()
            weatherViewModel.getWeatherInfo()?.observe(this, Observer {
                if (it!=null){
                    updateView(it)
                }

            })
        }
    }

    private fun updateView(value: MyWeatherModel?) {
        var data = value?.main;
        var temperature = data?.temp_max?.let { calculateTemperature(it) }
        mTemperatureText.setText(temperature?.toBigDecimal()?.toPlainString()+ " " + "°C")
    }

    private fun calculateTemperature(tempMax: Double): Double {
        return tempMax - 273.15
    }

    fun  isNetworkAvailbale():Boolean{
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo =conManager.activeNetworkInfo
        return internetInfo!=null && internetInfo.isConnected
    }
}

