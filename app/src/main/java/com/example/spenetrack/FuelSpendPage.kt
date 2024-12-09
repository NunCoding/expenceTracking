package com.example.spenetrack

import android.annotation.SuppressLint
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

class FuelSpendPage : AppCompatActivity() {
    private val sharedPreferencesKey = "FuelExpenseData"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fuel_spend_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView);
        val sharedPreferences = getSharedPreferences("FuelExpenseData", Context.MODE_PRIVATE)
        val tasks = sharedPreferences.getStringSet("tasks", mutableSetOf())?.toMutableList() ?: mutableListOf()

        val totalCurrentAmount = findViewById<TextView>(R.id.fuel_total_price);
        val totalFuelAmount = tasks.sumOf { task ->
            val parts = task.split(" - ")
            parts.getOrNull(1)?.toDoubleOrNull() ?: 0.00
        }
        val totalCurrentFuelExpense = totalFuelAmount.toFloat();
        totalCurrentAmount.text = totalCurrentFuelExpense.toString();
//      Pass the list to the adapter
        val adapter = FuelExpenseAdapter(this, tasks) { position ->
            tasks.removeAt(position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = adapter;

        val createFuel = findViewById<ImageView>(R.id.add_expense);
        createFuel.setOnClickListener{
            val intent = Intent(this,CreateFuelExpense::class.java);
            startActivity(intent);
        }
        val viewTransaction = findViewById<TextView>(R.id.view_transition);
        viewTransaction.setOnClickListener{
            val intent = Intent(this,TransactionPage::class.java);
            startActivity(intent)
        }
    }
}