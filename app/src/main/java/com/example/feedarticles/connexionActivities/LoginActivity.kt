package com.example.feedarticles.connexionActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedarticles.utils.Constants
import com.example.feedarticles.MainActivity
import com.example.feedarticles.R
import com.example.feedarticles.dtos.RegisterAndLoginDto
import com.example.feedarticles.network.loginUser

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
            val localDB = applicationContext
                .getSharedPreferences(Constants.KEY_NAME_APP, Context.MODE_PRIVATE)

            if(username.isNotEmpty() && password.isNotEmpty()){
                loginUser(RegisterAndLoginDto(username, password)){user, message ->
                    message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }

                    user?.let {
                        localDB
                            .edit()
                            .putLong(Constants.KEY_USER_ID, user.id)
                            .putString(Constants.KEY_USER_LOGIN, user.login)
                            .putString(
                            Constants.KEY_USER_PASSWORD, user.mdp)
                            .putString(Constants.KEY_USER_TOKEN, user.token)
                            .apply()

                        startActivity(Intent(this, MainActivity::class.java)
                            .putExtra(KEY_USER_DATA, user))
                        finish()

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