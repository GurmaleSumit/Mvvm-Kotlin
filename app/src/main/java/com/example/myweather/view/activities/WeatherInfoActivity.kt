package com.example.myweather.view.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myweather.R
import com.example.myweather.utils.PermissionUtils
import com.example.myweather.view.model.MyWeatherModel
import com.example.myweather.viewmodel.MyViewModelFactory
import com.example.myweather.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class WeatherInfoActivity : AppCompatActivity(), LifecycleOwner {

    lateinit var mTemperatureText: TextView
    lateinit var weatherViewModel: WeatherViewModel

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_info_activity)
        mTemperatureText = findViewById(R.id.temperature_tv);
        weatherViewModel = ViewModelProviders.of(
            this, MyViewModelFactory(this)
        ).get(WeatherViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.isAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtils.isLocationEnabled(this) -> {
                        setUpLocationListener()
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(this)
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    this,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //fusedLocationProviderClient.lastLocation.addOnCompleteListener()

            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                var location: Location? = task.result
                if (location == null) {
                    //NewLocation()
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        Toast.makeText(
                            applicationContext, "LatOnComplete :" +
                                    location.latitude.toString(), Toast.LENGTH_LONG
                        ).show()
                        loadWeatherInfo(location.latitude, location.longitude)
                    }, 2000)
                }
            }

            return
        }
    }

    fun loadWeatherInfo(latidude: Double, longitude: Double) {
        if (isNetworkAvailbale()) {
            weatherViewModel.init(latidude, longitude)
            weatherViewModel.getWeatherInfo()?.observe(this, Observer {
                if (it != null) {
                    updateView(it)
                }
            })
        }
    }

    private fun updateView(value: MyWeatherModel?) {
        var data = value?.main;
        var temperature = data?.temp_max?.let { calculateTemperature(it) }
        mTemperatureText.setText(temperature?.toBigDecimal()?.toPlainString() + " " + "°C")
    }

    private fun calculateTemperature(tempMax: Double): Double {
        return tempMax - 273.15
    }

    fun isNetworkAvailbale(): Boolean {
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo = conManager.activeNetworkInfo
        return internetInfo != null && internetInfo.isConnected
    }
}

