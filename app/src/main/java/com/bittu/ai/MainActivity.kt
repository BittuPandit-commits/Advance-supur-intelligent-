package com.bittu.ai

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<EditText>(R.id.taskInput)
        val output = findViewById<TextView>(R.id.chatOutput)
        val send = findViewById<Button>(R.id.sendButton)

        send.setOnClickListener {
            val message = input.text.toString().trim()
            if (message.isEmpty()) return@setOnClickListener

            output.append("\n\nYou: $message")
            input.text.clear()
            output.append("\n\nAdvance Super Intelligent: सोच रहा हूँ...")

            CoroutineScope(Dispatchers.Main).launch {
                val reply = GroqApi.chat(this@MainActivity, message)
                output.text = output.text.toString().replace(
                    "Advance Super Intelligent: सोच रहा हूँ...",
                    "Advance Super Intelligent: $reply"
                )
            }
        }
    }
}
