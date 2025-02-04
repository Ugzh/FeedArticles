package com.example.feedarticles

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.connexionActivities.LoginActivity
import com.example.feedarticles.dtos.ItemDto
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.mainRecyclerView.ItemsAdapter

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

        val itemAdapter: ItemsAdapter = ItemsAdapter().apply {
            setItems(arrayListOf(ItemDto("test", "x", 1, "x", 0, "x"),ItemDto("test2", "x", 2, "x", 0, "x")))
        }

        /*val rv = findViewById<RecyclerView>(R.id.rv_items)
        rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = itemAdapter
        }*/
    }
}