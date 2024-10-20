package com.shadows.tigers.fury

import android.annotation.SuppressLint
import android.content.Context

object Prefs {
    private lateinit var sharedPrefs: android.content.SharedPreferences
    private lateinit var statsPrefs: android.content.SharedPreferences

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        statsPrefs = context.getSharedPreferences("stats" + context.packageName, Context.MODE_PRIVATE)
    }

    var musicVolume: Float
        get() = sharedPrefs.getFloat("musicVolume", 0.5f)
        set(value) = sharedPrefs.edit().putFloat("musicVolume", value).apply()
    var soundVolume: Float
        get() = sharedPrefs.getFloat("soundVolume", 0.5f)
        set(value) = sharedPrefs.edit().putFloat("soundVolume", value).apply()

    @SuppressLint("SimpleDateFormat")
    fun addScore(score: Int) {
        val formatter = java.text.SimpleDateFormat("dd.MM.yyyy")
        val millis = System.currentTimeMillis()
        val date = java.util.Date(millis)
        val formattedDate = formatter.format(date)
        statsPrefs.edit().putInt(formattedDate, score).apply()
    }
    fun saveStarsForLevel(level: Int, stars: Int) {
        sharedPrefs.edit().putInt("level_$level", stars).apply()
    }

    fun getStarsForLevel(level: Int): Int {
        return sharedPrefs.getInt("level_$level", 0) // Default is 0 stars
    }

    var hightestLevelUnlocked get() = sharedPrefs.getInt("hightestLevelUnlocked", 1)
        set(value) = sharedPrefs.edit().putInt("hightestLevelUnlocked", value).apply()

    fun clearStats() {
        statsPrefs.edit().clear().apply()
    }

    val stats: List<Stat>
        get() {
            val stats = mutableListOf<Stat>()
            val keys = statsPrefs.all.keys
            for ((index, key) in keys.withIndex()) {
                stats.add(Stat(key, statsPrefs.getInt(key, 0), index + 1))
            }
            return stats.sortedBy { it.date }
        }
}

data class Stat(
    val date: String,
    val score: Int,
    val num: Int
)