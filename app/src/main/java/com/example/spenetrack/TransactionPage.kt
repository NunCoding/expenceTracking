package com.example.spenetrack

import ExpenseAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TransactionPage : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_page)

        val buttonbackListPage = findViewById<ImageView>(R.id.btnBackToListPage);
        buttonbackListPage.setOnClickListener{
            val intent = Intent(this,ListView::class.java);
            startActivity(intent);
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch all expense data
        val expenseList = fetchAllExpenseData()
        Log.d("FetchData", "Fetched Expenses: $expenseList")

        // Set up the adapter with the data
        val adapter = ExpenseAdapter(expenseList)
        recyclerView.adapter = adapter
    }

    private fun fetchAllExpenseData(): ArrayList<ExpenseItem> {
        val allExpenses = ArrayList<ExpenseItem>()
        val sharedPreferencesFiles = listOf("FoodExpenseData", "FuelExpenseData","SportExpenseData","OtherExpenseData")

        for (fileName in sharedPreferencesFiles) {
            val sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE)
            val tasks = sharedPreferences.getStringSet("tasks", mutableSetOf())?.toList() ?: mutableListOf()

            Log.d("TransactionData", tasks.toString())

            for (task in tasks) {
                val parts = task.split(" - ") // Example: "Category - Amount - Date"
                if (parts.size == 3) {
                    // Create ExpenseItem and add it to the list
                    allExpenses.add(ExpenseItem(parts[0], parts[1], parts[2]))
                }
            }
        }
        return allExpenses
    }
}
