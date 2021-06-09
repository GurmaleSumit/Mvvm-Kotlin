package com.example.myweather.workers

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myweather.network.ApiInterface
import com.example.myweather.network.RetrofitInstance

class MyWorker(
    @NonNull context: Context?,
    @NonNull workerParams: WorkerParameters?
) : Worker(context!!, workerParams!!) {
    private val weatherApi: ApiInterface
    init {
        weatherApi = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    }
    @NonNull
    override fun doWork(): Result {
        val data = inputData
        val desc = data.getString("LATITUDE")
        val data1 = Data.Builder()
            .putString(KEY_TASK_OUTPUT, "Task Finished Successfully")
            .build()
        return Result.success(data1)
    }

    companion object {
        const val KEY_TASK_OUTPUT = "key_task_output"
    }
}