package com.example.test1.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.*
import com.example.test1.database.NoteData
import com.example.test1.databinding.FragmentListBinding
import com.example.test1.pager.NotePagerActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListFragment : Fragment(), ListView {
    private lateinit var  binding: FragmentListBinding
    private lateinit var presenter: ListPresenter
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
        = FragmentListBinding.inflate(inflater,container, false).also {
         binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ListPresenter(this, requireContext())
             binding.about.setOnClickListener {
            presenter.btnAboutActivityClick()
        }
        binding.addNote.setOnClickListener {
            presenter.createNote()
        }
        presenter.loadAllNotes()
    }

    override fun openActivityAbout() {
        startActivity(Intent(requireContext(), AboutActivity::class.java))
    }

    override fun showNoteList(notes: Flow<List<NoteData>>) {
        lifecycleScope.launch {
            notes.collect {
                adapter = ListAdapter(it, ::openNote)
                val recyclerFragment: RecyclerView =
                    binding.recyclerView //тут find
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
