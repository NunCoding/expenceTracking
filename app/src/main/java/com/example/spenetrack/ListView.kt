package com.example.spenetrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        navigate to food list
        val foodPage = findViewById<View>(R.id.view);
        foodPage.setOnClickListener{
            val intent = Intent(this,FoodSpendPage::class.java);
            startActivity(intent);
        }

        val fuelPage = findViewById<View>(R.id.view5);
        fuelPage.setOnClickListener{
            val intent = Intent(this,FuelSpendPage::class.java);
            startActivity(intent);
        }

        val SportPage = findViewById<View>(R.id.view6);
        SportPage.setOnClickListener{
            val intent = Intent(this,SportSpendPage::class.java);
            startActivity(intent);
        }

        val OtherPage = findViewById<View>(R.id.view7);
        OtherPage.setOnClickListener{
            val intent = Intent(this,OtherSpendPage::class.java);
            startActivity(intent);
        }
    }
}