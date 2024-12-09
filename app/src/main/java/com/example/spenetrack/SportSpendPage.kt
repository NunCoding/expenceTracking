package com.example.spenetrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SportSpendPage : AppCompatActivity() {
    private val sharedPreferencesKey = "SportExpenseData"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sport_spend_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView);
        val sharedPreferences = getSharedPreferences("SportExpenseData", Context.MODE_PRIVATE)
        val tasks = sharedPreferences.getStringSet("tasks", mutableSetOf())?.toMutableList() ?: mutableListOf()

        val totalSportAmount = findViewById<TextView>(R.id.sport_total_price);
        val totalExpense = tasks.sumOf { task ->
            val parts = task.split(" - ")
            parts.getOrNull(1)?.toDoubleOrNull() ?: 0.0
        }
        val totalExpenseFloat = totalExpense.toFloat()
        totalSportAmount.text = totalExpenseFloat.toString();
//      Pass the list to the adapter
        val adapter = SportExpenseAdapter(this, tasks) { position ->
            tasks.removeAt(position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = adapter;

        val createSport = findViewById<ImageView>(R.id.create_sport);
        createSport.setOnClickListener{
            val intent = Intent(this,CreateSportExpense::class.java);
            startActivity(intent);
        }

        val viewTransaction = findViewById<TextView>(R.id.view_transition);
        viewTransaction.setOnClickListener{
            val intent = Intent(this,TransactionPage::class.java);
            startActivity(intent)
        }
    }
}