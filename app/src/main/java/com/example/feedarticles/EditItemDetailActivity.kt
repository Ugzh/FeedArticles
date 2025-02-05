package com.example.feedarticles

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.categoryRecyclerView.Category
import com.example.feedarticles.categoryRecyclerView.CategoryAdapter
import com.example.feedarticles.dtos.ItemDto
import com.example.feedarticles.dtos.UpdateItemDto
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.mainRecyclerView.ItemsAdapter
import com.example.feedarticles.network.updateItem
import com.squareup.picasso.Picasso

class EditItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item_detail)
        val list = listOf(Category("Sport"), Category("Manga"), Category("Divers"))
        val item : ItemDto? = intent.getParcelableExtra(ItemDetailActivity.KEY_ITEM_TO_EDIT_ITEM)
        val user : UserDto? = intent.getParcelableExtra(ItemDetailActivity.KEY_USER_TO_EDIT_ITEM)
        val ivImage = findViewById<ImageView>(R.id.img_editItemDetail_picture)
        val etUrlImage = findViewById<EditText>(R.id.et_editItemDetail_urlImage)
        val etItemTile = findViewById<EditText>(R.id.et_editItemDetail_itemTitle)
        val tvSecondTitle = findViewById<TextView>(R.id.tv_editItemDetail_secondTitle)
        val etDescription = findViewById<EditText>(R.id.et_editItemDetail_description)
        var categoryAdapter : CategoryAdapter? = null
        var categoryNum = 0
        val pbLoader = findViewById<ProgressBar>(R.id.pb_editItemDetail_loader)

        etUrlImage.setOnFocusChangeListener(){ view, isFocus ->
            if(!isFocus){
                val urlText = etUrlImage.text.toString().trim()
                Picasso
                    .get()
                    .load(urlText)
                    .placeholder(android.R.color.transparent)
                    .error(android.R.drawable.ic_menu_close_clear_cancel)
                    .into(ivImage)
                urlText.ifEmpty { ivImage.setImageResource(android.R.color.transparent) }
            }
        }

        item?.let {
            tvSecondTitle.text = it.title
            etItemTile.setText(it.title)
            etDescription.setText(it.description)
            etUrlImage.setText(it.urlImage)

            Picasso
                .get()
                .load(it.urlImage.ifEmpty { "boo" })
                .placeholder(android.R.drawable.ic_menu_search)
                .error(android.R.drawable.stat_notify_error)
                .into(ivImage)
        }

        val rv = findViewById<RecyclerView>(R.id.rv_editItemDetail)

        categoryAdapter = CategoryAdapter().apply {
            setCategories(list)
        }

        rv.apply {
            layoutManager = LinearLayoutManager(this@EditItemDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        categoryAdapter.apply {
            setSendCategoryCallback {

                categoryNum = when(it){
                    "Sport" -> 1
                    "Manga" -> 2
                    "Divers" -> 3
                    else -> return@setSendCategoryCallback
                }
            }
        }

        findViewById<Button>(R.id.btn_editItemDetail_editItem).setOnClickListener {
            val itemId = item?.id!!
            val itemTitle = etItemTile.text.toString().trim()
            val itemDescription = etDescription.text.toString().trim()
            val itemUrlImage = etUrlImage.text.toString().trim()


            pbLoader.visibility = View.VISIBLE
            updateItem(UpdateItemDto(itemId,
                itemTitle,
                itemDescription,
                itemUrlImage,
                categoryNum, user?.token!!)){ isInsert, message ->

                if(isInsert){
                    setResult(RESULT_OK, Intent().putExtra(KEY_ITEM_UPDATED_FOR_ITEM_DETAIL,ItemDto(itemId,itemTitle,itemDescription, itemUrlImage, categoryNum, item.createdAt, user.id)))
                    finish()
                } else {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                Toast.makeText(this@EditItemDetailActivity, message,
                    Toast.LENGTH_SHORT).show()
            }
            pbLoader.visibility = View.GONE


        }
        etUrlImage.setOnFocusChangeListener(){ _, hasFocus ->
            if(!hasFocus){
                val urlText = etUrlImage.text.toString().trim()
                Picasso
                    .get()
                    .load(urlText)
                    .placeholder(android.R.color.transparent)
                    .error(android.R.drawable.ic_menu_close_clear_cancel)
                    .into(ivImage)
                urlText.ifEmpty { ivImage.setImageResource(android.R.color.transparent) }
            }
        }

        etItemTile.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                val titleText = etItemTile.text.toString().trim()
                if(titleText.isNotEmpty()){
                    tvSecondTitle.text = titleText
                }
            }
        }
    }
    companion object{
        const val KEY_ITEM_UPDATED_FOR_ITEM_DETAIL = "KEY_ITEM_UPDATED_FOR_ITEM_DETAIL"
    }
}