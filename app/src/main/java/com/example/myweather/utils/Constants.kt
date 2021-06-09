/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myweather.utils

/**
 * Constants used throughout the app.
 */
const val DATABASE_NAME = "weather-db"
const val DB_NAME = "weather"
const val TABLE_NAME = "weathertable"
const val DB_VERSION = +1

const val DROP_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
const val GET_PRODUCTS_QUERY = "SELECT * FROM $TABLE_NAME"

const val KEY_ID = "keyid"
const val MAIN = "main"
const val DESCRIPTION = "description"
const val ICON = "icon"
const val TEMP = "temp"
const val HUMIDITY = "humidity"
const val TEMP_MIN = "temp_min"
const val TEMP_MAX = "temp_max"
const val SPEED = "speed"
const val COUNTRY = "country"
const val SUNRISE = "sunrise"
const val SUNSET = "sunset"
const val NAME = "name"

const val CREATE_TABLE_QUERY = ("CREATE TABLE " + TABLE_NAME + "("
        + KEY_ID + " INTEGER PRIMARY KEY,"
        + MAIN + " TEXT,"
        + DESCRIPTION + " TEXT,"
        + ICON + " TEXT,"
        + TEMP + " TEXT,"
        + HUMIDITY + " TEXT,"
        + TEMP_MIN + " TEXT,"
        + TEMP_MAX + " TEXT,"
        + SPEED + " TEXT,"
        + COUNTRY + " TEXT,"
        + SUNRISE + " TEXT,"
        + SUNSET + " TEXT,"
        + NAME + " TEXT" + ")")
