package com.example.workouts_johnsonhealthtech.data.local

import android.content.Context
import com.example.workouts_johnsonhealthtech.data.model.Difficulty
import com.example.workouts_johnsonhealthtech.data.model.Workout
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object InitialData {

    fun getWorkouts(context: Context): List<Workout> {
        val jsonString = context.assets.open("workouts.json").bufferedReader().use { it.readText() }
        val cleanedJsonString = jsonString.replace(Regex(",(\\s*[\\]}])"), "$1")
        val listType = object : TypeToken<List<Workout>>() {}.type
        val gson = GsonBuilder()
            .registerTypeAdapter(Difficulty::class.java, DifficultyDeserializer())
            .create()
        return gson.fromJson(cleanedJsonString, listType)
    }

    private class DifficultyDeserializer : JsonDeserializer<Difficulty> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Difficulty? {
            val jsonString = json?.asString?.trim()?.uppercase()
            return if (jsonString.isNullOrBlank()) {
                null
            } else {
                try {
                    Difficulty.valueOf(jsonString)
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
        }
    }
}