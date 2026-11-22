package com.bittu.ai

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val keyInput = findViewById<EditText>(R.id.groqKeyInput)
        val saveButton = findViewById<Button>(R.id.saveKeyButton)
        val status = findViewById<TextView>(R.id.keyStatus)

        val savedKey = GroqApi.getKey(this)
        status.text = if (savedKey.isNotBlank()) "Groq API key saved ✓" else "अभी कोई key saved नहीं है।"

        saveButton.setOnClickListener {
            val key = keyInput.text.toString().trim()
            if (key.isBlank()) {
                Toast.makeText(this, "पहले Groq API key paste करें", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GroqApi.saveKey(this, key)
            keyInput.text.clear()
            status.text = "Groq API key saved ✓"
            Toast.makeText(this, "Key saved", Toast.LENGTH_SHORT).show()
        }
    }
}
