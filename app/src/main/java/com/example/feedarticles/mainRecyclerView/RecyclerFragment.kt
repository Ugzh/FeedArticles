package com.example.feedarticles.mainRecyclerView

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.detailsItemActivities.ItemDetailActivity
import com.example.feedarticles.R
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.network.getAllItems
import com.example.feedarticles.network.getItemById

private const val ARG_USER = "ARG_USER"
class RecyclerFragment : Fragment() {
    private var user: UserDto? = null
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(ARG_USER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_recycler, container, false)

        val registerItemDetailDto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            refresh(view)
        }

        val rv = view.findViewById<RecyclerView>(R.id.rv_items)
        itemsAdapter = ItemsAdapter().apply {
            setItemDetailCallback { item ->
                getItemById(item, user!!){ itemFound, message ->
                    registerItemDetailDto.launch(Intent(view.context, ItemDetailActivity::class.java)
                        .apply {
                            putExtra(KEY_USER_TO_DETAIL_ACTIVITY, user)
                            putExtra(KEY_ITEM_TO_DETAIL_ACTIVITY, itemFound)
                        })
                }
            }
        }

        rv.apply {
            layoutManager = LinearLayoutManager(view.context)
            user?.let {
                getAllItems(it) { list, message ->
                    list?.let {
                        adapter = itemsAdapter
                    }
                    message?.let {
                        Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            refresh(view)
        }
        return view
    }

    fun refresh(view: View, categoryToFilter : Int? = 0){
        user?.let {
            getAllItems(it) { list, message ->
                list?.let { item ->
                   val sortedList = when(categoryToFilter){
                       1,2,3 -> item.filter { it.category == categoryToFilter }
                       else -> item
                   }
                    itemsAdapter.setItems(sortedList)
                }
                message?.let {
                    Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val KEY_USER_TO_DETAIL_ACTIVITY = "KEY_USER_TO_DETAIL_ACTIVITY"
        const val KEY_ITEM_TO_DETAIL_ACTIVITY = "KEY_ITEM_TO_DETAIL_ACTIVITY"
        @JvmStatic
        fun newInstance(user : UserDto) =
            RecyclerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER, user)
                }
            }
    }
}