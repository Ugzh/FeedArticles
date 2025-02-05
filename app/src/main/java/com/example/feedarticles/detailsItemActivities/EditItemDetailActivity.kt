package com.example.feedarticles.detailsItemActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.feedarticles.R
import com.example.feedarticles.Utils
import com.example.feedarticles.categoryRecyclerView.Category
import com.example.feedarticles.categoryRecyclerView.CategoryFragment
import com.example.feedarticles.dtos.ItemDto
import com.example.feedarticles.dtos.UpdateItemDto
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.network.updateItem
import com.squareup.picasso.Picasso
import java.lang.Exception

class EditItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item_detail)
        val item : ItemDto? = intent.getParcelableExtra(ItemDetailActivity.KEY_ITEM_TO_EDIT_ITEM)
        val user : UserDto? = intent.getParcelableExtra(ItemDetailActivity.KEY_USER_TO_EDIT_ITEM)
        val ivImage = findViewById<ImageView>(R.id.img_editItemDetail_picture)
        val etUrlImage = findViewById<EditText>(R.id.et_editItemDetail_urlImage)
        val etItemTile = findViewById<EditText>(R.id.et_editItemDetail_itemTitle)
        val tvSecondTitle = findViewById<TextView>(R.id.tv_editItemDetail_secondTitle)
        val etDescription = findViewById<EditText>(R.id.et_editItemDetail_description)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val categoryFragment = CategoryFragment.newInstance(Utils.arrayListOfCategoriesWithoutAll)

        ft.apply {
            replace(R.id.fl_editItemDetail_rvCategory,categoryFragment)
        }
        try {
            ft.commit()
        } catch (ex : Exception){
            Toast.makeText(this, "Impossible de récupérer les informations de la base de données", Toast.LENGTH_SHORT).show()
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

        findViewById<Button>(R.id.btn_editItemDetail_editItem).setOnClickListener {
            val itemId = item?.id!!
            val itemTitle = etItemTile.text.toString().trim()
            val itemDescription = etDescription.text.toString().trim()
            val itemUrlImage = etUrlImage.text.toString().trim()

            updateItem(UpdateItemDto(itemId,
                itemTitle,
                itemDescription,
                itemUrlImage,
                categoryFragment.getCategoryNum(), user?.token!!)){ isInsert, message ->

                if(isInsert){
                    setResult(RESULT_OK, Intent().putExtra(KEY_ITEM_UPDATED_FOR_ITEM_DETAIL,ItemDto(itemId,itemTitle,itemDescription, itemUrlImage, categoryFragment.getCategoryNum(), item.createdAt, user.id)))
                    finish()
                } else {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                Toast.makeText(this@EditItemDetailActivity, message,
                    Toast.LENGTH_SHORT).show()
            }

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

        findViewById<Button>(R.id.btn_editItemDetail_back).setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
    companion object{
        const val KEY_ITEM_UPDATED_FOR_ITEM_DETAIL = "KEY_ITEM_UPDATED_FOR_ITEM_DETAIL"
    }
}