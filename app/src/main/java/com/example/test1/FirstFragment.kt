package com.example.test1


import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button



import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


data class NoteData(var title: String, var subtitle: String)


class FirstFragment: Fragment() , FirstMainUi {
 private lateinit var noteFragment: RecyclerView
 private lateinit var adapter: CustomRecyclerAdapter
    private lateinit var btnAbout: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        val view = inflater.inflate(R.layout.recycler, container, false) //recycler ok
        noteFragment = view.findViewById(R.id.recyclerView) as RecyclerView //ok
        noteFragment.layoutManager = LinearLayoutManager(context)
        adapter = CustomRecyclerAdapter(notes)
        noteFragment.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAbout = view.findViewById(R.id.about)
        val presenter = FirstPresenter(this)
        btnAbout.setOnClickListener {
            presenter.activityBtnClick()
        }
    }

     private  var notes = mutableListOf<NoteData>()
    init {
        for (i in 0 until 100) {
            val note = NoteData(title = String(), subtitle = String())
            note.title = "Заголовок #$i"
            note.subtitle = "Текст #$i"
            notes += note
        }
    }
    override fun startIntent() {
        val intent = Intent(requireContext(), AboutActivity::class.java)
          startActivity(intent)
    }



}
