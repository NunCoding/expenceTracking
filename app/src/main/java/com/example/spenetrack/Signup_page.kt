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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Signup_page : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // initialize with firebase
        auth = Firebase.auth

        // declare variable
        val etUserName = findViewById<EditText>(R.id.txtUsername);
        val etEmail = findViewById<EditText>(R.id.txtEmail);
        val etPassword = findViewById<EditText>(R.id.txtpassword);
        val button = findViewById<Button>(R.id.btnSignup);

        // sign up action
        button.setOnClickListener{
            val username = etUserName.text.toString().trim();
            val email = etEmail.text.toString().trim();
            val password = etPassword.text.toString().trim();

            // validate with empty input
            if ( username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please fill out all fields", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

            if (password.length < 6){
                Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // create email and password into firebase
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task ->
                if (task.isSuccessful){
                    // Clear the EditText fields
                    etEmail.setText("")
                    etPassword.setText("")
                    // Signup success
                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,ListView::class.java);
                    startActivity(intent);
                }else{
                    // Signup failed
                    Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_LONG).show();
                    task.exception?.printStackTrace()
                }
            }

        }

        // login option
        val linkToLogin =  findViewById<TextView>(R.id.linkLogin);
        linkToLogin.setOnClickListener{
            val intent = Intent(this,LoginPage::class.java);
            startActivity(intent);
        }
    }
}