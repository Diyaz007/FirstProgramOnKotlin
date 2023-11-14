package com.example.myapplication.registration_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.main_window.Main_Window_Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val signUpName: EditText = findViewById(R.id.signUpName)
        val signUpEmail: EditText = findViewById(R.id.signUpEmail)
        val signUpPassword: EditText = findViewById(R.id.signUpPassword)
        val signUpCPassword: EditText = findViewById(R.id.signUpcPassword)

        val signUpBtn: Button = findViewById(R.id.signUpBtn)
        val signInText: TextView = findViewById(R.id.signUpText)
        signInText.setOnClickListener {
            val name = signUpName.text.toString()
            val email = signUpEmail.text.toString()
            val password = signUpPassword.text.toString()
            val cpassword = signUpCPassword.text.toString()
            if (!name.isEmpty() || !email.isEmpty() || !password.isEmpty() || !cpassword.isEmpty()) {
                signInText.error = "Complete the registration"
            } else {
                val intent = Intent(this, SingActivity::class.java)
                startActivity(intent)
            }
        }

        signUpBtn.setOnClickListener {
            val name = signUpName.text.toString()
            val email = signUpEmail.text.toString()
            val password = signUpPassword.text.toString()
            val cpassword = signUpCPassword.text.toString()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
                if (name.isEmpty()) {
                    signUpName.error = "Enter your name"
                }
                if (email.isEmpty()) {
                    signUpEmail.error = "Enter the correct email address"
                }
                if (password.isEmpty()) {
                    signUpPassword.error = "The password must not be empty"
                }
                if (cpassword.isEmpty()) {
                    signUpCPassword.error = "Passwords don't match"
                }
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            } else if (!email.matches(emailPattern.toRegex())) {
                signUpEmail.error = "Incorrect email address"
            } else if (password.length < 6) {
                signUpPassword.error = "The password must be more than 6 characters"
                Toast.makeText(this, "The password must be more than 6 characters", Toast.LENGTH_LONG)
                    .show()

            } else if (password != cpassword) {
                signUpCPassword.error = "The password doesn't match"
                Toast.makeText(this, "The password doesn't match", Toast.LENGTH_LONG).show()

            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val database =
                            database.reference.child("users").child(auth.currentUser!!.uid)
                        val users: Users = Users(name, email, auth.currentUser!!.uid)
                        database.setValue(users).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, Main_Window_Activity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Something went wrong, try again",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}