package com.example.feedarticles

import android.content.Intent
import android.os.Bundle
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


        findViewById<TextView>(R.id.tv_main_userTitle).text = userData?.login

        findViewById<TextView>(R.id.tv_main_logout).setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        findViewById<ImageView>(R.id.img_main_logout).setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val recyclerFragment = RecyclerFragment.newInstance(userData ?: UserDto(1,"Ugo","ugo123","4c70ecf0c8bd69311a7634e0d38f4694"))
        val categoryFragment = CategoryFragment.newInstance(arrayListOf(Category("Sport"), Category("Manga"), Category("Divers"), Category("Tous")))
        ft.apply {
            replace(R.id.fl_main_rvCategory,categoryFragment)
            replace(R.id.fl_main_rvMain,recyclerFragment)
        }
        try {
            ft.commit()
        } catch (ex : Exception){
            Toast.makeText(this, "Impossible de récupérer les informations de la base de données", Toast.LENGTH_SHORT).show()
        }

        val registerCreateItemForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}

        findViewById<Button>(R.id.btn_main_addItem).setOnClickListener {
            registerCreateItemForResult.launch(Intent(this, CreateItemActivity::class.java).apply { putExtra(KEY_USER_DATA_TO_CREATE_ITEM, userData) })
        }
    }

    companion object{
        const val KEY_USER_DATA_TO_CREATE_ITEM = "KEY_USER_DATA_TO_CREATE_ITEM"
    }
}