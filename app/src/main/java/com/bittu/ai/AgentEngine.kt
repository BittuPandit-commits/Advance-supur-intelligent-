package com.bittu.ai

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AgentEngine {

    suspend fun runTask(
        context: Context,
        moduleName: String,
        task: String
    ): AgentResult = withContext(Dispatchers.IO) {
        val intent = ToolRouter.detectIntent(task)
        val toolInstruction = ToolRouter.toolInstruction(intent)

        MemoryStore.addMessage(context, "user", "[$moduleName] $task")
        MemoryStore.saveContext(context, "last_module", moduleName)
        MemoryStore.saveContext(context, "last_intent", intent)

        val planningPrompt = """
            Task: $task
            Module: $moduleName
            Tool intent: $intent

            Create a concise execution plan for this task.
            Return only a numbered plan with 3 to 6 practical steps.
        """.trim()

        val plan = AiBrain.think(context, planningPrompt, moduleName, agentMode = true)

        val executionPrompt = """
            $toolInstruction

            Original task: $task
            Module: $moduleName
            Approved plan:
            $plan

            Now execute the task as completely as possible.
            Provide the final deliverable, not only advice.
            End with:
            Goal:
            Plan:
            Result:
            Next Actions:
        """.trim()

        val result = AiBrain.think(context, executionPrompt, moduleName, agentMode = true)
        MemoryStore.addMessage(context, "assistant", result)

        AgentResult(
            intent = intent,
            plan = plan,
            result = result
        )
    }
}

data class AgentResult(
    val intent: String,
    val plan: String,
    val result: String
)
