package com.bittu.ai

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object GroqApi {

    private const val endpoint = "https://api.groq.com/openai/v1/chat/completions"

    fun saveKey(context: Context, key: String) {
        context.getSharedPreferences("bittu_ai", Context.MODE_PRIVATE)
            .edit()
            .putString("groq_api_key", key.trim())
            .apply()
    }

    fun getKey(context: Context): String {
        return context.getSharedPreferences("bittu_ai", Context.MODE_PRIVATE)
            .getString("groq_api_key", "") ?: ""
    }

    suspend fun chat(context: Context, userMessage: String): String = withContext(Dispatchers.IO) {
        val key = getKey(context)
        if (key.isBlank()) {
            return@withContext "पहले Settings में Groq API key डालें।"
        }

        try {
            val connection = URL(endpoint).openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Authorization", "Bearer $key")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.connectTimeout = 20000
            connection.readTimeout = 60000

            val body = JSONObject().apply {
                put("model", "llama-3.3-70b-versatile")
                put("temperature", 0.7)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", "You are Advance Super Intelligent, a helpful AI assistant created by Bittu Pandit. Reply in the user's language.")
                    })
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", userMessage)
                    })
                })
            }

            connection.outputStream.use { it.write(body.toString().toByteArray()) }

            val responseText = if (connection.responseCode in 200..299) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                connection.errorStream?.bufferedReader()?.use { it.readText() } ?: ""
            }
            connection.disconnect()

            if (connection.responseCode !in 200..299) {
                return@withContext "Groq error: ${connection.responseCode}\n\n$responseText"
            }

            val response = JSONObject(responseText)
            response.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
        } catch (e: Exception) {
            "AI response नहीं मिल सका।\n\n${e.message}"
        }
    }
}
