package com.bittu.ai

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object MemoryStore {
    private const val PREFS = "advance_super_intelligent_memory"
    private const val KEY_HISTORY = "conversation_history"
    private const val KEY_CONTEXT = "project_context"

    fun addMessage(context: Context, role: String, content: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val history = JSONArray(prefs.getString(KEY_HISTORY, "[]"))
        val message = JSONObject().put("role", role).put("content", content)
        history.put(message)
        while (history.length() > 40) {
            history.remove(0)
        }
        prefs.edit().putString(KEY_HISTORY, history.toString()).apply()
    }

    fun history(context: Context): JSONArray {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return JSONArray(prefs.getString(KEY_HISTORY, "[]"))
    }

    fun saveContext(context: Context, key: String, value: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val obj = JSONObject(prefs.getString(KEY_CONTEXT, "{}"))
        obj.put(key, value)
        prefs.edit().putString(KEY_CONTEXT, obj.toString()).apply()
    }

    fun context(context: Context): JSONObject {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return JSONObject(prefs.getString(KEY_CONTEXT, "{}"))
    }

    fun clear(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().clear().apply()
    }
}
