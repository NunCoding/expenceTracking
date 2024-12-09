package com.example.spenetrack

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListView : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
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
        val foodPage = findViewById<View>(R.id.navigate_to_food);
        foodPage.setOnClickListener{
            val intent = Intent(this,FoodSpendPage::class.java);
            startActivity(intent);
        }

        val fuelPage = findViewById<View>(R.id.navigate_to_fuel);
        fuelPage.setOnClickListener{
            val intent = Intent(this,FuelSpendPage::class.java);
            startActivity(intent);
        }

        val SportPage = findViewById<View>(R.id.navigate_to_sport);
        SportPage.setOnClickListener{
            val intent = Intent(this,SportSpendPage::class.java);
            startActivity(intent);
        }

        val OtherPage = findViewById<View>(R.id.navigate_to_other);
        OtherPage.setOnClickListener{
            val intent = Intent(this,OtherSpendPage::class.java);
            startActivity(intent);
        }

        val showTransaction = findViewById<ImageView>(R.id.show_transaction);
        showTransaction.setOnClickListener{
            val intent = Intent(this,TransactionPage::class.java);
            startActivity(intent);
        }

        val topUpButton = findViewById<ImageView>(R.id.btn_top_top);
        topUpButton.setOnClickListener{
            showInputDialog();
        }

        // map total amount to food
        val sharedPreferences = getSharedPreferences("FoodExpenseData", Context.MODE_PRIVATE)
        val tasks = sharedPreferences.getStringSet("tasks", mutableSetOf())?.toMutableList() ?: mutableListOf()
        val totalFoodExpens = findViewById<TextView>(R.id.total_food_amount);
        val totalExpense = tasks.sumOf { task ->
            val parts = task.split(" - ")
            parts.getOrNull(1)?.toDoubleOrNull() ?: 0.00
        }
        val totalExpenseFloat = totalExpense.toFloat()
        totalFoodExpens.text = "$ " + totalExpenseFloat.toString();

        // map total amount to fuel
        val sharedPreferencesFuel = getSharedPreferences("FuelExpenseData", Context.MODE_PRIVATE)
        val fuelTasks = sharedPreferencesFuel.getStringSet("tasks", mutableSetOf())?.toMutableList() ?: mutableListOf()
        val totalFuelExpense = findViewById<TextView>(R.id.total_fuel_expense);
        val totalFuelAmount = fuelTasks.sumOf { task ->
            val parts = task.split(" - ")
            parts.getOrNull(1)?.toDoubleOrNull() ?: 0.00
        }
        val totalFuelExpenseFloat = totalFuelAmount.toFloat()
        totalFuelExpense.text = "$ " + totalFuelExpenseFloat.toString();

        // map total amount to sport
        val sharedPreferencesSport = getSharedPreferences("SportExpenseData", Context.MODE_PRIVATE)
        val SportTasks = sharedPreferencesSport.getStringSet("tasks", mutableSetOf())?.toMutableList() ?: mutableListOf()
        val totalSportExpense = findViewById<TextView>(R.id.total_sport_expense);
        val totalSportAmount = SportTasks.sumOf { task ->
            val parts = task.split(" - ")
            parts.getOrNull(1)?.toDoubleOrNull() ?: 0.00
        }
        val totalSportExpenseFloat = totalSportAmount.toFloat()
        totalSportExpense.text = "$ " + totalSportExpenseFloat.toString();

        // map total amount to other
        val sharedPreferencesOther = getSharedPreferences("OtherExpenseData", Context.MODE_PRIVATE)
        val OtherTasks = sharedPreferencesOther.getStringSet("tasks", mutableSetOf())?.toMutableList() ?: mutableListOf()
        val totalOtherExpense = findViewById<TextView>(R.id.total_other_expnse);
        val totalOtherAllAmount = findViewById<TextView>(R.id.total_other_amount);
        val totalOtherAmount = OtherTasks.sumOf { task ->
            val parts = task.split(" - ")
            parts.getOrNull(1)?.toDoubleOrNull() ?: 0.00
        }
        val totalOtherExpenseFloat = totalOtherAmount.toFloat()
        totalOtherExpense.text = "$ " + totalOtherExpenseFloat.toString();
        totalOtherAllAmount.text = "$ " + totalOtherExpenseFloat.toString();

        // all expense
        val allOfExpense = findViewById<TextView>(R.id.current_money_expense);
        val allAmountExpense = totalExpense + totalFuelAmount + totalSportAmount + totalOtherAmount;
        allOfExpense.text = "$ " + allAmountExpense.toString();
    }

    @SuppressLint("WrongViewCast")
    fun showInputDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter your amount")
        val input = EditText(this);
        input.hint = "amount"
        builder.setView(input);

        builder.setPositiveButton("Ok"){
            dialog,_ ->
            val userInput = input.text.toString();
            val topUpAmount = findViewById<TextView>(R.id.top_up_amount);
            topUpAmount.text = userInput;
            dialog.dismiss()
        }
        builder.setNegativeButton("cancel"){dialog,_ ->
            dialog.cancel()
        }
        builder.show();
    }

}