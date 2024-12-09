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

class OtherSpendPage : AppCompatActivity() {
    private val sharedPreferencesKey = "OtherExpenseData"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_other_spend_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView);
        val sharedPreferences = getSharedPreferences("OtherExpenseData", Context.MODE_PRIVATE)
        val tasks = sharedPreferences.getStringSet("tasks", mutableSetOf())?.toMutableList() ?: mutableListOf()

        val totalOtherAmount = findViewById<TextView>(R.id.other_total_price);
        val totalExpense = tasks.sumOf { task ->
            val parts = task.split(" - ")
            parts.getOrNull(1)?.toDoubleOrNull() ?: 0.0
        }
        val totalExpenseFloat = totalExpense.toFloat()
        totalOtherAmount.text = totalExpenseFloat.toString();

//      Pass the list to the adapter
        val adapter = OtherExpenseAdapter(this, tasks) { position ->
            tasks.removeAt(position)
        }


        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = adapter;

        val createOther = findViewById<ImageView>(R.id.create_other);
        createOther.setOnClickListener{
            val intent = Intent(this,CreateOtherExpense::class.java);
            startActivity(intent);
        }

        val viewTransaction = findViewById<TextView>(R.id.view_transition);
        viewTransaction.setOnClickListener{
            val intent = Intent(this,TransactionPage::class.java);
            startActivity(intent)
        }
    }
}