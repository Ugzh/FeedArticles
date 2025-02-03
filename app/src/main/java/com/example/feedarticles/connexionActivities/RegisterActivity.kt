package com.example.feedarticles.connexionActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedarticles.R
import com.example.feedarticles.dtos.RegisterAndLoginDto
import com.example.feedarticles.network.registerUser

class RegisterActivity : AppCompatActivity() {
    companion object{
        const val KEY_USER_DATA = "KEY_USER_DATA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<Button>(R.id.btn_createAccount_validate).setOnClickListener {
            val username = findViewById<EditText>(R.id.et_createAccount_username)
                .text
                .toString()
                .trim()
            val password = findViewById<EditText>(R.id.et_createAccount_password)
                .text
                .toString()
                .trim()
            val confirmPassword = findViewById<EditText>(R.id.et_createAccount_confirmPassword)
                .text
                .toString()
                .trim()

            if (username.isNotEmpty() && (password.isNotEmpty() == confirmPassword.isNotEmpty())){
                registerUser(RegisterAndLoginDto(username,password)){ message, statusCode ->
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    if(statusCode == 1){
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        findViewById<TextView>(R.id.tv_createAccount_alreadyAccount).setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}