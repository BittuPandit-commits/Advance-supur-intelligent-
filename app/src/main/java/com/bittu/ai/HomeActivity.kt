package com.bittu.ai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val grid = findViewById<GridLayout>(R.id.featureGrid)

        FeatureCatalog.all.forEach { feature ->
            val card = layoutInflater.inflate(R.layout.item_feature, grid, false) as android.widget.LinearLayout
            card.findViewById<TextView>(R.id.featureTitle).text = feature.title
            card.findViewById<TextView>(R.id.featureCategory).text = feature.category

            card.setOnClickListener {
                val intent = Intent(this, FeatureActivity::class.java)
                intent.putExtra("title", feature.title)
                intent.putExtra("description", feature.description)
                intent.putExtra("category", feature.category)
                startActivity(intent)
            }

            grid.addView(card)
        }

        findViewById<Button>(R.id.openChatButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
