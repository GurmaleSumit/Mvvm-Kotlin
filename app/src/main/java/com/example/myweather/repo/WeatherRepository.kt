package com.example.myweather.repo

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.myweather.network.ApiInterface
import com.example.myweather.network.RetrofitInstance
import com.example.myweather.view.model.MyWeatherModel
import com.example.myweather.workers.MyWorker
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
        context: LifecycleOwner,
        latitude: Double?,
        longitude: Double?,
        appid: String?
    ): MutableLiveData<MyWeatherModel> {

        var weatherData: MutableLiveData<MyWeatherModel> = MutableLiveData()

        val data = Data.Builder()
            .putString("LATITUDE", latitude.toString())
            .putString("LONGITUDE", longitude.toString())
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueue(request)
        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
            .observe(context, Observer { workInfo ->
                // Check if the current work's state is "successfully finished"
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    var data = workInfo.outputData
                    if (data.getString(MyWorker.KEY_TASK_OUTPUT).equals("Task Finished Successfully")) {
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
                    }
                }
            })

        return weatherData
    }
}
