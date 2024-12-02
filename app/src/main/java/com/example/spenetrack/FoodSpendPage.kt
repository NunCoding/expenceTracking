package com.example.spenetrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FoodSpendPage : AppCompatActivity() {
    private val sharedPreferencesKey = "FoodExpenseData"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_food_spend_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView);
        val sharedPreferences = getSharedPreferences("FoodExpenseData", Context.MODE_PRIVATE)
        val tasks = sharedPreferences.getStringSet("tasks", mutableSetOf())?.toMutableList() ?: mutableListOf()

// Pass the list to the adapter
        val adapter = FoodSpendAdapter(this, tasks) { position ->
            tasks.removeAt(position)
        }


        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = adapter;

        val foodExpense = findViewById<ImageView>(R.id.create_food_expense)
        foodExpense.setOnClickListener {
            val intent = Intent(this, CreateFoodExpense::class.java)
            startActivity(intent)
        }

        // back to list page
        val backButton = findViewById<ImageView>(R.id.btn_back_home);
        backButton.setOnClickListener{
            val intent = Intent(this,ListView::class.java);
            startActivity(intent);
        }
    }
}
