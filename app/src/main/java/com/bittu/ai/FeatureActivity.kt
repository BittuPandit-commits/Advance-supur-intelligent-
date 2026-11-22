package com.bittu.ai

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)

        val title = intent.getStringExtra("title") ?: "Advance Super Intelligent"
        val description = intent.getStringExtra("description") ?: ""
        val category = intent.getStringExtra("category") ?: ""

        findViewById<TextView>(R.id.featureTitle).text = title
        findViewById<TextView>(R.id.featureDescription).text = description
        findViewById<TextView>(R.id.featureCategory).text = category

        val output = findViewById<TextView>(R.id.featureOutput)
        val input = findViewById<EditText>(R.id.featureInput)
        val scroll = findViewById<ScrollView>(R.id.featureScroll)

        findViewById<Button>(R.id.runButton).setOnClickListener {
            val task = input.text.toString().trim()

            if (task.isNotEmpty()) {
                output.append("\n\nआपका कार्य: $task")
                output.append("\n\nAdvance Super Intelligent Agent: कार्य योजना बना रहा हूँ...")
                scroll.post { scroll.fullScroll(ScrollView.FOCUS_DOWN) }
                input.text.clear()

                CoroutineScope(Dispatchers.Main).launch {
                    val agentResult = AgentEngine.runTask(
                        this@FeatureActivity,
                        title,
                        task
                    )

                    val finalText = """
                        \n\nIntent: ${agentResult.intent}
                        \n\nExecution Plan:
                        ${agentResult.plan}
                        \n\nFinal Result:
                        ${agentResult.result}
                    """.trim()

                    output.append(finalText)
                    scroll.post { scroll.fullScroll(ScrollView.FOCUS_DOWN) }
                }
            }
        }
    }
}
