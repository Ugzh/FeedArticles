package com.example.feedarticles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.feedarticles.connexionActivities.LoginActivity
import com.example.feedarticles.dtos.NewItemDto
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.mainRecyclerView.RecyclerFragment
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userData : UserDto?  = intent.getParcelableExtra(LoginActivity.KEY_USER_DATA)


        findViewById<TextView>(R.id.tv_main_userTitle).text = userData?.login

        findViewById<TextView>(R.id.tv_main_logout).setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val recyclerFragment = RecyclerFragment.newInstance(userData ?: UserDto(1,"Ugo","ugo123","4c70ecf0c8bd69311a7634e0d38f4694"))
        ft.apply {
            replace(R.id.fl_main,recyclerFragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        try {
            ft.commit()
        } catch (ex : Exception){
            Toast.makeText(this, "Impossible de récupérer les informations de la base de données", Toast.LENGTH_SHORT).show()
        }
    }
}