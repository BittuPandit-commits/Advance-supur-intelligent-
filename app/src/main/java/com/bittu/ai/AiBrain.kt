package com.bittu.ai

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

object AiBrain {

    suspend fun think(
        context: Context,
        userMessage: String,
        moduleName: String = "General",
        agentMode: Boolean = false
    ): String {
        return withContext(Dispatchers.IO) {
            val history = MemoryStore.history(context)
            val projectContext = MemoryStore.context(context)

            val system = buildSystemPrompt(moduleName, agentMode, projectContext)
            val messages = JSONArray().put(
                JSONObject().put("role", "system").put("content", system)
            )

            for (i in 0 until history.length()) {
                val item = history.getJSONObject(i)
                messages.put(
                    JSONObject()
                        .put("role", item.optString("role", "user"))
                        .put("content", item.optString("content", ""))
                )
            }

            messages.put(JSONObject().put("role", "user").put("content", userMessage))
            GroqApi.chatMessages(context, messages)
        }
    }

    private fun buildSystemPrompt(
        moduleName: String,
        agentMode: Boolean,
        projectContext: JSONObject
    ): String {
        val base = StringBuilder()
        base.append("You are Advance Super Intelligent, an advanced AI system created by Bittu Pandit. ")
        base.append("Reply in the user's language. Be accurate, practical, and structured.\n\n")
        base.append("Active module: $moduleName.\n")

        if (agentMode) {
            base.append("\nAgent mode instructions:\n")
            base.append("1. Understand the user's final goal.\n")
            base.append("2. Create a clear step-by-step execution plan.\n")
            base.append("3. Identify required information, tools, risks, and assumptions.\n")
            base.append("4. Produce the best possible complete result.\n")
            base.append("5. End with a short final report containing: Goal, Plan, Result, Next Actions.\n")
        }

        if (projectContext.length() > 0) {
            base.append("\nSaved project context:\n")
            base.append(projectContext.toString(2))
        }

        return base.toString()
    }
}
