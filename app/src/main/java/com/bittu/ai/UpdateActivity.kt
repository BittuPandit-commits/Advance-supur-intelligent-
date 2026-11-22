package com.bittu.ai

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class UpdateActivity : AppCompatActivity() {

    private val repoApi = "https://api.github.com/repos/BittuPandit-commits/Advance-supur-intelligent-/releases/latest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val status = findViewById<TextView>(R.id.updateStatus)
        val action = findViewById<Button>(R.id.updateAction)

        action.setOnClickListener {
            status.text = "नया update जाँचा जा रहा है..."
            checkUpdate(status, action)
        }

        checkUpdate(status, action)
    }

    private fun checkUpdate(status: TextView, action: Button) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    val connection = URL(repoApi).openConnection() as HttpURLConnection
                    connection.setRequestProperty("Accept", "application/vnd.github+json")
                    connection.connectTimeout = 10000
                    connection.readTimeout = 10000

                    val text = connection.inputStream.bufferedReader().use { it.readText() }
                    connection.disconnect()
                    text
                }

                val release = org.json.JSONObject(result)
                val version = release.optString("tag_name", "latest")
                val notes = release.optString("body", "")
                val assets: JSONArray = release.optJSONArray("assets") ?: JSONArray()

                var apkUrl = ""
                for (i in 0 until assets.length()) {
                    val asset = assets.getJSONObject(i)
                    if (asset.optString("name").endsWith(".apk")) {
                        apkUrl = asset.optString("browser_download_url")
                        break
                    }
                }

                status.text = "Latest version: $version\n\n$notes"

                if (apkUrl.isNotEmpty()) {
                    action.text = "Update Download करें"
                    action.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(apkUrl)))
                    }
                } else {
                    status.append("\n\nअभी कोई APK release उपलब्ध नहीं है।")
                    action.text = "फिर से जाँचें"
                }
            } catch (e: Exception) {
                status.text = "Update जाँच नहीं हो सका।\n\nInternet connection जाँचें और फिर से try करें।"
                action.text = "फिर से जाँचें"
            }
        }
    }
}
