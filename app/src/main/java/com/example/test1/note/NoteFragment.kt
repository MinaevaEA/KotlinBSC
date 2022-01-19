package com.example.test1.note


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.test1.R
import com.example.test1.database.ConcreteNoteDatabase
import com.example.test1.database.NoteData
import com.example.test1.databinding.FragmentNoteBinding
import com.example.test1.pager.NotePagerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var viewModelFactory: NoteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNoteBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments
        val inputData = args?.getSerializable(NOTE_DATA) as? NoteData
        viewModelFactory = NoteViewModelFactory(ConcreteNoteDatabase.getDatabase(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
        if (inputData != null) {
            viewModel.initData(inputData)
        }
        subscribeToViewModel()
        binding.buttonShare.setOnClickListener {
            viewModel.share()
        }

        binding.title.addTextChangedListener { viewModel.updateTitle(it?.toString().orEmpty()) }
        binding.content.addTextChangedListener { viewModel.updateText(it?.toString().orEmpty()) }
    }

    override fun onResume() {
        super.onResume()
        (activity as? NotePagerActivity)?.currentFragment = this
    }

    private fun onSaveSuccess(title: String, text: String) {
        showNotification(R.string.save_msg)
        activity?.sendBroadcast(Intent().apply {
            action = ACTION
            putExtra(NOTE_DATA, "${title}\n${text}")
        })
    }

    private fun onNoteEmpty() {
        showNotification(R.string.msg_error)
    }

    private fun showNotification(msg_toast: Int) {
        lifecycleScope.launch {
            Toast.makeText(requireContext(), msg_toast, Toast.LENGTH_SHORT).show()
        }
    }

    private fun share(noteData: NoteData) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "${noteData.title}\n${noteData.text}")
        }
        startActivity(shareIntent)
    }

    private fun showNote(noteData: NoteData?) {
        if (binding.title.text.toString() != noteData?.title) {
            binding.title.setText(noteData?.title)
        }
        if (binding.content.text.toString() != noteData?.text) {
            binding.content.setText(noteData?.text)
        }
    }

    fun save() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.save()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.noteData.observe(requireActivity()) {
            showNote(it)
        }
        viewModel.noteShare.observe(requireActivity()) {
            share(it)
        }
        viewModel.noteEmpty.observe(requireActivity()) {
            onNoteEmpty()
        }
        viewModel.saveSuccess.observe(requireActivity()) {
            onSaveSuccess(it.title,it.text)
        }

    }

    companion object {
        private const val NOTE_DATA: String = "note_text"
        private const val ACTION = "com.example.test1.ACTION_SAVING"
        fun newInstance(noteData: NoteData): NoteFragment = NoteFragment().apply {
            arguments = Bundle().apply { putSerializable(NOTE_DATA, noteData) }
        }
    }
}