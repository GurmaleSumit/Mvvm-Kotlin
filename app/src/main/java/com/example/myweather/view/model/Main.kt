package com.example.myweather.view.model

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    var temp_max: Double,
    var temp_min: Double
)