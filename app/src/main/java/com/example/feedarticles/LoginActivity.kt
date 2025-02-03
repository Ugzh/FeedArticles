package com.example.feedarticles

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
import com.example.feedarticles.dtos.RegisterAndLoginDto

class LoginActivity : AppCompatActivity() {
    
    companion object {
        const val KEY_USER_DATA = "KEY_USER_DATA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btn_login_validate).setOnClickListener {
            val username = findViewById<EditText>(R.id.et_login_username).text.toString().trim()
            val password = findViewById<EditText>(R.id.et_login_password).text.toString().trim()

            if(username.isNotEmpty() && password.isNotEmpty()){
                loginUser(RegisterAndLoginDto(username, password)){user, message ->
                    message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }

                    user?.let {
                        startActivity(Intent(this, MainActivity::class.java).putExtra(KEY_USER_DATA, user))
                    }
                }
            } else
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
        }


        findViewById<TextView>(R.id.tv_login_noAccount).setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}