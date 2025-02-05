package com.example.feedarticles.detailsItemActivities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.feedarticles.MainActivity
import com.example.feedarticles.R
import com.example.feedarticles.utils.Utils
import com.example.feedarticles.categoryRecyclerView.CategoryFragment
import com.example.feedarticles.dtos.NewItemDto
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.network.createNewItem
import java.lang.Exception

class CreateItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_item)

        val user : UserDto? = intent.getParcelableExtra(MainActivity.KEY_USER_DATA_TO_CREATE_ITEM)
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val categoryFragment = CategoryFragment.newInstance(Utils.arrayListOfCategoriesWithoutAll)

        ft.apply {
            replace(R.id.fl_createItem,categoryFragment)
        }
        try {
            ft.commit()
        } catch (ex : Exception){
            Toast.makeText(this,
                "Impossible de récupérer les informations de la base de données", Toast.LENGTH_SHORT)
                .show()
        }

        findViewById<Button>(R.id.btn_createItem_create).setOnClickListener {
            val etTitle = findViewById<EditText>(R.id.et_createItem_title)
                         .text
                         .toString()
                         .trim()
            val etDescription = findViewById<EditText>(R.id.et_createItem_description)
                                .text
                                .toString()
                                .trim()
            val etUrlImg = findViewById<EditText>(R.id.et_createItem_urlImage)
                            .text
                            .toString()
                            .trim()
            val categoryNum = categoryFragment.getCategoryNum()


            if(etTitle.isNotEmpty()
                && etDescription.isNotEmpty()
                && etUrlImg.isNotEmpty()
                && categoryNum != 0){
                user?.let {
                    createNewItem(
                        NewItemDto(user.id, etTitle, etDescription, etUrlImg, categoryNum, user.token))
                    { isCreated, message ->
                        if(isCreated){
                            setResult(RESULT_OK)
                            finish()
                        } else{
                            Toast.makeText(this,message, Toast.LENGTH_SHORT)
                                 .show()
                        }
                    }
                }
            } else
                Toast.makeText(this,"Veuillez remplir tous les champs, et selectionner une catégorie", Toast.LENGTH_SHORT)
                    .show()
        }

        findViewById<Button>(R.id.btn_createItem_back).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}