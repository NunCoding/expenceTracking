package com.example.spenetrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // initialize auth with firebase
        auth = FirebaseAuth.getInstance()

        // declare variable
        val etEmail = findViewById<EditText>(R.id.txtUsername);
        val etPass = findViewById<EditText>(R.id.txtpassword);
        val buttonLogin = findViewById<Button>(R.id.btnLogin);

        // click login action
        buttonLogin.setOnClickListener{
            val email = etEmail.text.toString().trim();
            val password = etPass.text.toString().trim();

            // validation with empty input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // validate with user and password in firebase
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    // Clear the EditText fields
                    etEmail.setText("")
                    etPass.setText("")

                    // success message
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    // navigate to list action page
                    val intent = Intent(this,ListView::class.java);
                    startActivity(intent);
                }else{
                    // Login failed
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        // sign up option
        val link = findViewById<TextView>(R.id.linkSignup);
        link.setOnClickListener{
            val intent = Intent(this,Signup_page::class.java);
            startActivity(intent)
        }

    }
}