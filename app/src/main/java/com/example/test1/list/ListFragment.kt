package com.example.test1.list

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.*
import com.example.test1.database.ConcreteNoteDatabase
import com.example.test1.database.NoteData
import com.example.test1.databinding.FragmentListBinding
import com.example.test1.pager.NotePagerActivity
import kotlinx.coroutines.launch


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: AllNotesViewModel
    private lateinit var viewModelFactory: AllNotesViewModelFactory
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentListBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFactory =
            AllNotesViewModelFactory(ConcreteNoteDatabase.getDatabase(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[AllNotesViewModel::class.java]
        subscribeToViewModel()

        binding.about.setOnClickListener {
            viewModel.btnAboutActivityClick()
        }
        binding.addNote.setOnClickListener {
            viewModel.createNote()
        }
        binding.buttonDownloadNote.setOnClickListener {
            viewModel.downloadNote()
        }
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNotes(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchNotes(newText)
                return true
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAllNotes()
    }

    private fun showNoteList(notes: List<NoteData>) {
        adapter = ListAdapter(notes, ::openNote)
        val recyclerFragment: RecyclerView =
            binding.recyclerView
        recyclerFragment.layoutManager = LinearLayoutManager(context)
        recyclerFragment.adapter = adapter
    }

    private fun showNotification(msg_toast: Int) {
        lifecycleScope.launch {
            Toast.makeText(requireContext(), msg_toast, Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.notes.observe(requireActivity()) {
            showNoteList(it)
        }
        viewModel.openNote.observe(requireActivity()) { (notes, currentPosition) ->
            NotePagerActivity.startActivity(requireContext(), notes, currentPosition)
        }
        viewModel.openAbout.observe(requireActivity()) {
            startActivity(Intent(requireContext(), AboutActivity::class.java))
        }
        viewModel.errorDownloadNote.observe(requireActivity()) {
            showNotification(R.string.msg_error_download_note)
        }
    }

    private fun openNote(notes: List<NoteData>, currentPosition: Int) {
        viewModel.openNote(notes, currentPosition)
    }
}

