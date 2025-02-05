package com.example.feedarticles

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.feedarticles.categoryRecyclerView.Category
import com.example.feedarticles.categoryRecyclerView.CategoryFragment
import com.example.feedarticles.connexionActivities.LoginActivity
import com.example.feedarticles.detailsItemActivities.CreateItemActivity
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.mainRecyclerView.RecyclerFragment
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userData : UserDto?  = intent.getParcelableExtra(LoginActivity.KEY_USER_DATA)
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val recyclerFragment = RecyclerFragment.newInstance(userData!!)
        val categoryFragment = CategoryFragment.newInstance(Utils.arrayListOfAllCategories).apply {
            setFragment(recyclerFragment)
        }

        val registerCreateItemForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                recyclerFragment.refresh(findViewById(android.R.id.content))
            }
        }

        findViewById<TextView>(R.id.tv_main_userTitle).text = userData.login

        findViewById<TextView>(R.id.tv_main_logout).setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.img_main_logout).setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.btn_main_addItem).setOnClickListener {
            registerCreateItemForResult.launch(Intent(this, CreateItemActivity::class.java).apply { putExtra(KEY_USER_DATA_TO_CREATE_ITEM, userData) })
        }

        ft.apply {
            replace(R.id.fl_main_rvCategory,categoryFragment)
            replace(R.id.fl_main_rvMain,recyclerFragment)
        }
        try {
            ft.commit()
        } catch (ex : Exception){
            Toast.makeText(this, "Impossible de récupérer les informations de la base de données", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val KEY_USER_DATA_TO_CREATE_ITEM = "KEY_USER_DATA_TO_CREATE_ITEM"
    }
}