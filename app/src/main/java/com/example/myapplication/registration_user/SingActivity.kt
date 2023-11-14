package com.example.myapplication.registration_user

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.R
import com.example.myapplication.main_window.Main_Window_Activity
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SingActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing)
        auth=FirebaseAuth.getInstance()
        val signInEmail: EditText = findViewById(R.id.signInEmail)
        val signInPassword: EditText = findViewById(R.id.signInPassword)
        val signInBtn: AppCompatButton = findViewById(R.id.signInBtn)
        val signUpText: TextView = findViewById(R.id.signInText)
        val text: TextView = findViewById(R.id.forgotPassword)
        signUpText.setOnClickListener{
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        signInBtn.setOnClickListener {
            val email=signInEmail.text.toString()
            val password=signInPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                if(email.isEmpty()){
                    signInEmail.error = "Write email"
                }
                if(password.isEmpty()){
                    signInPassword.error = "Write password"
                }
                Toast.makeText(this,"Enter your details",Toast.LENGTH_LONG).show()
            }
            else if(!email.matches(emailPattern.toRegex())){
                signInEmail.error="Enter a valid email"
                Toast.makeText(this,"Invalid email",Toast.LENGTH_LONG).show()
            }else if (password.length < 6){
                signInPassword.error = "The password must be more than 6 characters"
                Toast.makeText(this,"The password must be more than 6 characters",Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent=Intent(this,Main_Window_Activity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()

                    }
                }
            }
        }
        text.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view=layoutInflater.inflate(R.layout.forgot_password,null)
            val userEmail = view.findViewById<EditText>(R.id.emailBox)
            builder.setView(view)
            val dialog=builder.create()
            view.findViewById<Button>(R.id.btnReset).setOnClickListener {
                compareEmail(userEmail)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if(dialog.window!=null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
        }
    }
    private fun compareEmail(email:EditText) {
        if(email.text.toString().isEmpty()){
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        auth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this,"Check your email",Toast.LENGTH_LONG).show()
            }
        }

    }
}