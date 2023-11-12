package com.homelandpay.codigodietplan.data

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.homelandpay.codigodietplan.R
import com.homelandpay.codigodietplan.data.modal.AllergiesEntity
import com.homelandpay.codigodietplan.data.modal.Data
import com.homelandpay.codigodietplan.data.modal.DietEntity
import com.homelandpay.codigodietplan.data.modal.HealthConcernEntity
import java.io.IOException

class PreferencesManager(val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val HEALTH_CONCERN_ITEM = "concern_item"
    private val DIET_ITEM = "diet_item"
    private val OPTIONAL_ALGERIES = "optional_algeries"
    private val COMBINE_JSON_STRING = "private_json_string"

    fun getAllDiets():List<DietEntity.Diets>{
        return try {
            val inputStream = context.resources.openRawResource(R.raw.diet)
            val json = inputStream.bufferedReader().use { it.readText() }
            val allergies = Gson().fromJson(json, DietEntity::class.java)
            allergies.diets
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getAllHealthConcern():List<HealthConcernEntity.HealthConcern>{
        return try {
            val inputStream = context.resources.openRawResource(R.raw.health_concern)
            val json = inputStream.bufferedReader().use { it.readText() }
            val allergies = Gson().fromJson(json, HealthConcernEntity::class.java)
            allergies.healthConcern
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getAllergies(): List<Data> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.allergies)
            val json = inputStream.bufferedReader().use { it.readText() }
            val allergies = Gson().fromJson(json, AllergiesEntity::class.java)
            allergies.data
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }


    fun saveHealthConcernItem(dataList: List<HealthConcernEntity.HealthConcern>) {
        val jsonString = Gson().toJson(dataList)
        sharedPreferences.edit().putString(HEALTH_CONCERN_ITEM, jsonString).apply()
    }

    fun getHealthConcernItem(): List<HealthConcernEntity.HealthConcern>? {
        val gson = Gson()
        val json =sharedPreferences.getString(HEALTH_CONCERN_ITEM, "")
        val type = object : TypeToken<List<HealthConcernEntity.HealthConcern>?>() {}.type
        return gson.fromJson(json, type)

    }

    fun saveDietItem(dataList: List<DietEntity.Diets>) {
        val jsonString = Gson().toJson(dataList)
        sharedPreferences.edit().putString(DIET_ITEM, jsonString).apply()
    }

    fun getDietItem(): List<DietEntity.Diets>? {
        val gson = Gson()
        val json =sharedPreferences.getString(DIET_ITEM, "")
        val type = object : TypeToken<List<DietEntity.Diets>?>() {}.type
        return gson.fromJson(json, type)

    }

    fun saveOptionalAlgeries(dataList: List<String>) {
        val jsonString = Gson().toJson(dataList)
        sharedPreferences.edit().putString(OPTIONAL_ALGERIES, jsonString).apply()
    }

    fun getOptionalAlgeries(): List<String>? {
        val gson = Gson()
        val json =sharedPreferences.getString(OPTIONAL_ALGERIES, "")
        val type = object : TypeToken<List<String>?>() {}.type
        return gson.fromJson(json, type)

    }

    fun saveCombineJson(jsonString: String) {
        sharedPreferences.edit().putString(COMBINE_JSON_STRING, jsonString).apply()
    }

    fun getCombineJson(): String {
        return sharedPreferences.getString(COMBINE_JSON_STRING, "") ?: ""
    }
}