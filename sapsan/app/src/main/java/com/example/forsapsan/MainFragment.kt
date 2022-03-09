package com.example.forsapsan

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


data class Rate(val title: String, val price: String, val content: String)

class MainFragment : Fragment() {

    private val rate = listOf(
        Rate("6 месяцев", "${1990} руб.", "Пробный период три дня, бесплатная доставка"),
        Rate("1 месяц", "${499} руб.", "Ежемесячная подписка"),
        Rate("Навсегда", "${4990} руб.", "Один платеж")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_main, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerFragment: RecyclerView =
            view.findViewById(R.id.list_recycler_view) as RecyclerView
        recyclerFragment.layoutManager = LinearLayoutManager(activity)
        val adapter = ListAdapter(rate)
        recyclerFragment.adapter = adapter
        adapter.setOnClickListener(object : ListAdapter.OnItemClickListener {

            override fun onItemClick(position: Int) {

                Toast.makeText(context, "ура $position", Toast.LENGTH_SHORT).show()

            }
        })
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}