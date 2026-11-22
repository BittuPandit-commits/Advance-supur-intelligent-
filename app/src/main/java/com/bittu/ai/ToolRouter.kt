package com.bittu.ai

object ToolRouter {

    fun detectIntent(message: String): String {
        val text = message.lowercase()
        return when {
            text.contains("code") || text.contains("कोड") || text.contains("app") ||
                text.contains("website") || text.contains("वेबसाइट") -> "coding"

            text.contains("research") || text.contains("शोध") || text.contains("compare") ||
                text.contains("तुलना") || text.contains("fact") -> "research"

            text.contains("pdf") || text.contains("word") || text.contains("excel") ||
                text.contains("report") || text.contains("रिपोर्ट") || text.contains("document") -> "document"

            text.contains("plan") || text.contains("योजना") || text.contains("strategy") ||
                text.contains("रणनीति") -> "planning"

            else -> "general"
        }
    }

    fun toolInstruction(intent: String): String {
        return when (intent) {
            "coding" -> "Act as a senior software engineer. Provide architecture, complete code, file structure, setup steps, testing steps, and potential improvements."
            "research" -> "Act as a careful research assistant. Separate facts, assumptions, sources to verify, and conclusions. Do not present unverified claims as facts."
            "document" -> "Create a clean, structured document draft with headings, bullet points, tables where useful, and a professional conclusion."
            "planning" -> "Create a practical execution plan with goals, milestones, resources, risks, timeline, and success metrics."
            else -> "Understand the request deeply and provide a clear, useful, well-structured answer."
        }
    }
}
