package com.example.spenetrack

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateFoodExpense : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_food_expense)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance().reference
        val editSpendType = findViewById<EditText>(R.id.food_spend)
        val amount = findViewById<EditText>(R.id.amount)
        val date = findViewById<EditText>(R.id.Date)
        val buttonCreate = findViewById<ImageView>(R.id.button_create);
        val priceShow = findViewById<TextView>(R.id.price_show);

        amount.addTextChangedListener{
            val price = amount.text.toString();
            priceShow.text = price;
        }

        buttonCreate.setOnClickListener{
            val spend = editSpendType.text.toString();
            val prices = amount.text.toString();
            val dateTime = date.text.toString();
            if (spend.isNotEmpty() && prices.isNotEmpty() && dateTime.isNotEmpty()){
               val amountSpend = prices.toDoubleOrNull()
                if (amountSpend != null){
                    val spendData = SpendData(spend,amountSpend,dateTime);
                    val key = database.push().key ?: ""
                    database.child("spend_food").child(key).setValue(spendData)
                        .addOnSuccessListener {
                            showToast("Data saved successfully")
                            editSpendType.text.clear()
                            amount.text.clear()
                            date.text.clear()
                        }
                        .addOnFailureListener{
                            showToast("Please enter a valid amount")
                        }
                }else{
                    showToast("Please fill in all fields")
                }
            }
        }

        val buttonBack = findViewById<ImageView>(R.id.btn_back);
        buttonBack.setOnClickListener{
            val intent = Intent(this,FoodSpendPage::class.java);
            startActivity(intent);
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
// SpendData.kt
data class SpendData(
    val spend: String = "",
    val prices: Double = 0.0,
    val dateTime: String = ""
)
