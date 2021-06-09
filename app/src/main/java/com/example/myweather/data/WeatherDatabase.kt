package com.example.myweather.data

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.myweather.utils.*
import com.example.myweather.view.model.MyWeatherModel
import java.util.*

class WeatherDatabase(context: Context?) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_QUERY)
        } catch (ex: SQLException) {
            Log.d(TAG, ex.message!!)
        }
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL(DROP_QUERY)
        onCreate(db)
        db.close()
    }

    fun deleteProducts() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun addDataInDB(
        s: String?,
        s0: String?,
        s1: String?,
        s2: String?,
        s3: String?,
        s4: String?,
        s5: String?,
        s6: String?,
        s7: String?,
        s8: String?,
        s9: String?,
        s10: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(MAIN, s)
        values.put(DESCRIPTION, s0)
        values.put(ICON, s1)
        values.put(TEMP, s2)
        values.put(HUMIDITY, s3)
        values.put(TEMP_MIN, s4)
        values.put(TEMP_MAX, s5)
        values.put(SPEED, s6)
        values.put(COUNTRY, s7)
        values.put(SUNRISE, s8)
        values.put(SUNSET, s9)
        values.put(NAME, s10)
        try {
            db.insert(TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.d(TAG, e.message!!)
        }
        db.close()
    }

    val allData: List<Any>
        get() {
            var contactList: MutableList<MyWeatherModel> =
                ArrayList<MyWeatherModel>()
            val db = this.writableDatabase
            val cursor =
                db.rawQuery(GET_PRODUCTS_QUERY, null)
            if (cursor.moveToFirst()) {
                do {
                    var contact : MyWeatherModel? = null
                    //contact.setHumidity(cursor.getString(cursor.getColumnIndex(HUMIDITY)))
                    contact?.main?.temp_min = cursor.getString(cursor.getColumnIndex(TEMP_MIN)).toDouble()
                    contact?.main?.temp_max = cursor.getString(cursor.getColumnIndex(TEMP_MAX)).toDouble()
                    /*contact.setSpeed(cursor.getString(cursor.getColumnIndex(SPEED)))
                    contact.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)))
                    contact.setSunrise(cursor.getString(cursor.getColumnIndex(SUNRISE)))
                    contact.setSunset(cursor.getString(cursor.getColumnIndex(SUNSET)))
                    contact.setName(cursor.getString(cursor.getColumnIndex(NAME)))*/
                    contact?.let { contactList.add(it) }
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return contactList
        }

    fun updateValues(
        x: String,
        s: String?,
        s0: String?,
        s1: String?,
        s2: String?,
        s3: String?,
        s4: String?,
        s5: String?,
        s6: String?,
        s7: String?,
        s8: String?,
        s9: String?,
        s10: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(MAIN, s)
        values.put(DESCRIPTION, s0)
        values.put(ICON, s1)
        values.put(TEMP, s2)
        values.put(HUMIDITY, s3)
        values.put(TEMP_MIN, s4)
        values.put(TEMP_MAX, s5)
        values.put(SPEED, s6)
        values.put(COUNTRY, s7)
        values.put(SUNRISE, s8)
        values.put(SUNSET, s9)
        values.put(NAME, s10)

        // updating row
        db.update(
            TABLE_NAME,
            values,
            KEY_ID.toString() + " = ?",
            arrayOf(x)
        )
        db.close()
    }

    fun TableNotEmpty(): Boolean {
        val db = writableDatabase
        val mCursor =
            db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        val rowExists: Boolean
        if (mCursor.moveToFirst()) {
            // DO SOMETHING WITH CURSOR
            mCursor.close()
            rowExists = true
            db.close()
        } else {
            // I AM EMPTY
            mCursor.close()
            rowExists = false
            db.close()
        }
        db.close()
        return rowExists
    }

    companion object {
        private val TAG = WeatherDatabase::class.java.simpleName
        private val TABLE_NAME = WeatherDatabase::class.java.name
    }
}