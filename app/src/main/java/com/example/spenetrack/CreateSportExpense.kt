package com.example.spenetrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener

class CreateSportExpense : AppCompatActivity() {
    private val sharedPreferencesKey = "SportExpenseData"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_sport_expense)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spendTypeEd = findViewById<EditText>(R.id.sport_expense)
        val amountEd = findViewById<EditText>(R.id.amount)
        val dateTimeEd = findViewById<EditText>(R.id.Date)
        val createButton = findViewById<ImageView>(R.id.create_sport_expense)

        val prices = findViewById<TextView>(R.id.price_show);
        amountEd.addTextChangedListener {
            val amount = amountEd.text.toString();
            prices.text = "$ ${amount}";
        }
        createButton.setOnClickListener {
            val spendType = spendTypeEd.text.toString()
            val amount = amountEd.text.toString()
            val date = dateTimeEd.text.toString()

            if (spendType.isNotEmpty() && amount.isNotEmpty() && date.isNotEmpty()) {
                val newTask = "$spendType - $amount - $date"

                // Save to SharedPreferences
                val sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
                val existingTasks = sharedPreferences.getStringSet("tasks", mutableSetOf()) ?: mutableSetOf()
                existingTasks.add(newTask)
                sharedPreferences.edit().putStringSet("tasks", existingTasks).apply()

                // Clear input fields
                spendTypeEd.text.clear()
                amountEd.text.clear()
                dateTimeEd.text.clear()

                Toast.makeText(this, "Task added Successfully!", Toast.LENGTH_SHORT).show()

                // Navigate to FoodSpendPage
                val intent = Intent(this, SportSpendPage::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        val createFuel = findViewById<ImageView>(R.id.btn_back);
        createFuel.setOnClickListener{
            val intent = Intent(this,SportSpendPage::class.java);
            startActivity(intent);
        }
    }
}