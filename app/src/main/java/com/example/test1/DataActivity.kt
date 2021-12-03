package com.example.test1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


abstract class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_activity)
            initRecyclerView()
        }
        private fun initRecyclerView(){
            findViewById<RecyclerView>(R.id.recyclerView).apply {
                adapter = CustomRecyclerAdapter(populate())
            }
        }


    private fun populate(): List<NoteData> = listOf(
        NoteData("Заголовок", "Текст1"),
        NoteData("Заголовок2", "Текст2"),
        NoteData("Заголовок3", "Текст3"),
        NoteData("Заголовок4", "Текст4"),
        NoteData("Заголовок5", "Текст5"),
        NoteData("Заголовок6", "Текст6"),
    )
}








