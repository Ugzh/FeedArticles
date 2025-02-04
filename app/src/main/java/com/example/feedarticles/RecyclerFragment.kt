package com.example.feedarticles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.dtos.UserDto
import com.example.feedarticles.mainRecyclerView.ItemsAdapter
import com.example.feedarticles.network.getAllItems

private const val ARG_USER = "ARG_USER"
class RecyclerFragment() : Fragment() {
    private var user: UserDto? = null

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

        val rv = view.findViewById<RecyclerView>(R.id.rv_items)
        rv.apply {
            layoutManager = LinearLayoutManager(view.context)
            user?.let {
                getAllItems(it){ list, message ->

                    list?.let {
                        adapter = ItemsAdapter().apply {
                            setItems(it)
                        }
                    }

                    message?.let {
                        Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(user : UserDto) =
            RecyclerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER, user)

                }
            }
    }
}