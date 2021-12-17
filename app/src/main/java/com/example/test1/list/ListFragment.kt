package com.example.test1.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.*
import com.example.test1.database.NoteData
import com.example.test1.pager.NotePagerActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListFragment : Fragment(), ListMainView {
    private lateinit var presenter: ListPresenter
    private lateinit var adapter: ListAdapter
    private lateinit var btnAboutClick: Button
    private lateinit var btnCreateNote: ImageButton
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_list, container, false) //recycler ok

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCreateNote = view.findViewById(R.id.add_note)
        presenter = ListPresenter(this, requireContext())
        toolbar = view.findViewById(R.id.toolbarMain)
        btnAboutClick = view.findViewById(R.id.about)
        btnAboutClick.setOnClickListener {
            presenter.btnAboutActivityClick()
        }
        btnCreateNote.setOnClickListener {
            val newNote = NoteData(0, "", "")
            val newNoteList = mutableListOf<NoteData>().apply {
                addAll(presenter.notes)
                add(newNote)
            }
            presenter.notes = newNoteList
            presenter.openNote(newNoteList, newNoteList.size - 1)
        }
        presenter.loadAllNotes()
    }

    override fun openActivityAbout() {
        startActivity(Intent(requireContext(), AboutActivity::class.java))
    }

    override fun showNoteList(notes: Flow<List<NoteData>>) {
        lifecycleScope.launch {
            notes.collect {
                presenter.notes = it
                adapter = ListAdapter(it, ::openNote)
                val recyclerFragment: RecyclerView =
                    view?.findViewById(R.id.recyclerView) as RecyclerView //ok
                recyclerFragment.layoutManager = LinearLayoutManager(context)
                recyclerFragment.adapter = adapter
            }
        }
    }

    override fun onNoteOpen(notes: List<NoteData>, currentPosition: Int) {
        context?.let {
            NotePagerActivity.startActivity(it, notes, currentPosition)
        }
    }

    private fun openNote(notes: List<NoteData>, currentPosition: Int) {
        presenter.openNote(notes, currentPosition)
    }
}
