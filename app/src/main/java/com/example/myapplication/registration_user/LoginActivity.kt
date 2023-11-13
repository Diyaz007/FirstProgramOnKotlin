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
                signInText.error = "Завершите регистрацию"
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
                    signUpName.error = "Введите свое имя"
                }
                if (email.isEmpty()) {
                    signUpEmail.error = "Введите коректно email адрес"
                }
                if (password.isEmpty()) {
                    signUpPassword.error = "Пароль не должен быть пустым"
                }
                if (cpassword.isEmpty()) {
                    signUpCPassword.error = "Пароли не совпадают"
                }
                Toast.makeText(this, "Что то пошло не так", Toast.LENGTH_LONG).show()
            } else if (!email.matches(emailPattern.toRegex())) {
                signUpEmail.error = "Не корестный email адрес"
            } else if (password.length < 6) {
                signUpPassword.error = "Пароль должен быть больше 6 символов"
                Toast.makeText(this, "Пароль должен быть больше 6 символов", Toast.LENGTH_LONG)
                    .show()

            } else if (password != cpassword) {
                signUpCPassword.error = "Пароль не совпадает"
                Toast.makeText(this, "Пароль не совпадает", Toast.LENGTH_LONG).show()

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
                                    "Что то пошло не так ,попробуйте снова",
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