package com.bittu.ai

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var chatOutput: TextView
    private lateinit var taskInput: EditText
    private lateinit var scroll: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatOutput = findViewById(R.id.chatOutput)
        taskInput = findViewById(R.id.taskInput)
        scroll = findViewById(R.id.scroll)

        findViewById<Button>(R.id.sendButton).setOnClickListener {
            val task = taskInput.text.toString().trim()

            if (task.isNotEmpty()) {
                chatOutput.append("\n\nआप: $task")
                chatOutput.append("\n\nBittu AI: मैं आपके कार्य के लिए कोड बनाने की तैयारी कर रहा हूँ।")
                scroll.post { scroll.fullScroll(ScrollView.FOCUS_DOWN) }
                taskInput.text.clear()
            }
        }
    }
}
