package com.example.feedarticles

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.feedarticles.connexionActivities.LoginActivity
import com.example.feedarticles.dtos.ItemDto
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.mainRecyclerView.RecyclerFragment
import com.squareup.picasso.Picasso

class ItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val userData : UserDto?  = intent
            .getParcelableExtra(RecyclerFragment.KEY_USER_TO_DETAIL_ACTIVITY)
        val itemData : ItemDto?  = intent
            .getParcelableExtra(RecyclerFragment.KEY_ITEM_TO_DETAIL_ACTIVITY)
        val tvCategory : TextView = findViewById(R.id.tv_itemDetail_category)
        val tvTitle = findViewById<TextView>(R.id.tv_itemDetail_secondTitle)
        val tvItemTitle : TextView = findViewById(R.id.tv_itemDetail_itemTitle)
        val tvDescription : TextView = findViewById(R.id.tv_itemDetail_description)
        val image = findViewById<ImageView>(R.id.img_itemDetail_picture)

        itemData?.let {
            findViewById<TextView>(R.id.tv_itemDetail_secondTitle).text = it.title
            tvCategory.text = when(it.category){
                1 -> "Sport"
                2 -> "Manga"
                3 -> "Divers"
                else -> "Inconnu"
            }
            tvItemTitle.text = it.title
            tvDescription.text = it.description

            Picasso
                .get()
                .load(it.urlImage.ifEmpty { "boo" })
                .placeholder(android.R.drawable.ic_menu_search)
                .error(android.R.drawable.stat_notify_error)
                .into(image)
        }

        userData?.let {
            val registerEditItemDetailDto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activity ->
                if(activity.resultCode == Activity.RESULT_OK){
                    activity.data?.let {intent ->
                        intent.getParcelableExtra<ItemDto?>(EditItemDetailActivity.KEY_ITEM_UPDATED_FOR_ITEM_DETAIL)?.let { item ->
                            tvItemTitle.text = item.title
                            tvDescription.text = item.description
                            Log.d("test", item.category.toString())
                            tvCategory.text= when(item.category){
                                1 -> "Sport"
                                2 -> "Manga"
                                3 -> "Divers"
                                else -> "Inconnu"
                            }
                            tvTitle.text = item.title
                            Picasso
                                .get()
                                .load(item.urlImage.ifEmpty { "boo" })
                                .placeholder(android.R.drawable.ic_menu_search)
                                .error(android.R.drawable.stat_notify_error)
                                .into(image)
                        }


                    }
                }
            }

            if (it.id == itemData?.idU){
                findViewById<Button>(R.id.btn_itemDetail_editItem).let { btn->
                    btn.visibility = View.VISIBLE
                    btn.setOnClickListener {
                        registerEditItemDetailDto.launch(Intent(this, EditItemDetailActivity::class.java).apply {
                            putExtra(KEY_ITEM_TO_EDIT_ITEM, itemData)
                            putExtra(KEY_USER_TO_EDIT_ITEM, userData)
                        })
                    }
                }

            }

        }

        findViewById<Button>(R.id.btn_itemDetail_back).setOnClickListener {
            finish()
        }
    }

    companion object{
        const val KEY_ITEM_TO_EDIT_ITEM = "KEY_ITEM_TO_EDIT_ITEM"
        const val KEY_USER_TO_EDIT_ITEM = "KEY_USER_TO_EDIT_ITEM"
    }
}