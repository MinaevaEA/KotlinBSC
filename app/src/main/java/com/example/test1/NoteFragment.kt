package com.example.test1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoteFragment: Fragment() {

    private lateinit var initNoteRecycler: RecyclerView
    private var adapter: CustomRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {
        val view = inflater.inflate(R.layout.list_item, container, false)

        initNoteRecycler = view.findViewById(R.id.recyclerView) as RecyclerView
        initNoteRecycler.layoutManager = LinearLayoutManager(context)


        return view
    }


}
