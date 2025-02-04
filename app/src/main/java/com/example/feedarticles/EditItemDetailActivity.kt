package com.example.feedarticles

import android.content.ClipData.Item
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.categoryRecyclerView.Category
import com.example.feedarticles.categoryRecyclerView.CategoryAdapter
import com.example.feedarticles.dtos.ItemDto
import com.example.feedarticles.mainRecyclerView.ItemsAdapter
import com.squareup.picasso.Picasso

class EditItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item_detail)
        val list = listOf(Category("Sport"), Category("Manga"), Category("Divers"))
        val item : ItemDto? = intent.getParcelableExtra(ItemDetailActivity.KEY_ITEM_TO_EDIT_ITEM)
        val ivImage = findViewById<ImageView>(R.id.img_editItemDetail_picture)
        val etUrlImage = findViewById<EditText>(R.id.et_editItemDetail_urlImage)
        val etItemTile = findViewById<EditText>(R.id.et_editItemDetail_itemTitle)
        val tvSecondTitle = findViewById<TextView>(R.id.tv_editItemDetail_secondTitle)

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

        etItemTile.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val titleText = etItemTile.text.toString().trim()
                if(titleText.isNotEmpty()){
                    tvSecondTitle.text = titleText
                }
            }
        }

        item?.let {
            tvSecondTitle.text = it.title
            etItemTile.setText(it.title)
            findViewById<EditText>(R.id.et_editItemDetail_description).setText(it.description)
            etUrlImage.setText(it.urlImage)

            Picasso
                .get()
                .load(it.urlImage.ifEmpty { "boo" })
                .placeholder(android.R.drawable.ic_menu_search)
                .error(android.R.drawable.stat_notify_error)
                .into(ivImage)
        }

        val rv = findViewById<RecyclerView>(R.id.rv_editItemDetail)
        rv.apply {
            layoutManager = LinearLayoutManager(this@EditItemDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter().apply {
                setCategories(list)
            }
        }

    }
}