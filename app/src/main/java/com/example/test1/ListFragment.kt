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


class ListFragment : Fragment(), ListMainView {
    private lateinit var adapter: FragmentAdapter
    private lateinit var btnAbout: Button
    private var notes = mutableListOf<NoteData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recycler, container, false) //recycler ok
        val recyclerFragment: RecyclerView =
            view.findViewById(R.id.recyclerView) as RecyclerView //ok
        recyclerFragment.layoutManager = LinearLayoutManager(context)
        adapter = FragmentAdapter(notes)
        recyclerFragment.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val presenter = ListPresenter(this)
        btnAbout = view.findViewById(R.id.about)
        btnAbout.setOnClickListener {
            presenter.btnAboutActivityClick()
        }
    }

    init {
        for (i in 0 until 100) {
            val note = NoteData(title = String(), subtitle = String())
            note.title = "Заголовок #$i"
            note.subtitle = "Текст #$i"
            notes += note
        }
    }

    override fun openActivityAbout() {
        val intent = Intent(requireContext(), AboutActivity::class.java)
        startActivity(intent)
    }
}
