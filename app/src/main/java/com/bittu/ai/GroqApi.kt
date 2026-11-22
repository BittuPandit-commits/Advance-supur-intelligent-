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
    private const val model = "llama-3.3-70b-versatile"

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
        val messages = JSONArray().apply {
            put(
                JSONObject().apply {
                    put("role", "system")
                    put(
                        "content",
                        "You are Advance Super Intelligent, a helpful AI assistant created by Bittu Pandit. Reply in the user's language. Use clean plain text. Do not use markdown symbols such as asterisks, hashes, hyphens, equal signs, plus signs, or backticks."
                    )
                }
            )
            put(JSONObject().apply { put("role", "user").put("content", userMessage) })
        }
        chatMessages(context, messages)
    }

    suspend fun chatMessages(context: Context, messages: JSONArray): String = withContext(Dispatchers.IO) {
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
                put("model", model)
                put("temperature", 0.7)
                put("messages", messages)
            }

            connection.outputStream.use { it.write(body.toString().toByteArray()) }

            val responseCode = connection.responseCode
            val responseText = if (responseCode in 200..299) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                connection.errorStream?.bufferedReader()?.use { it.readText() } ?: ""
            }
            connection.disconnect()

            if (responseCode !in 200..299) {
                return@withContext when (responseCode) {
                    401 -> "Groq ने आपकी API key स्वीकार नहीं की। कृपया Groq Console में नई key बनाएं और Settings में दोबारा save करें।\n\nTechnical details:\n$responseText"
                    404 -> "Groq ने request का model या service नहीं पाया। कृपया कुछ देर बाद फिर कोशिश करें।\n\nTechnical details:\n$responseText"
                    429 -> "Groq की limit पूरी हो गई है। कृपया कुछ देर बाद फिर कोशिश करें।\n\nTechnical details:\n$responseText"
                    else -> "Groq से जवाब नहीं मिला। Error code: $responseCode\n\nTechnical details:\n$responseText"
                }
            }

            val content = JSONObject(responseText)
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")

            cleanPlainText(content)
        } catch (e: Exception) {
            "AI response नहीं मिल सका।\n\n${e.message}"
        }
    }

    private fun cleanPlainText(text: String): String {
        return text
            .replace("```", "")
            .replace("**", "")
            .replace("##", "")
            .replace("###", "")
            .replace("*", "")
            .replace("=", "")
            .replace("+", "")
            .replace("- ", "")
            .replace("\n\n\n+", "\n\n")
            .trim()
    }
}
