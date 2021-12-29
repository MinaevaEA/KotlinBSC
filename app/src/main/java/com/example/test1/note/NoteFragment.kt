package com.example.test1.note


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.test1.R
import com.example.test1.database.NoteData
import com.example.test1.databinding.FragmentNoteBinding
import com.example.test1.pager.NotePagerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel
    private val textChangedListener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            viewModel.updateText(s?.toString().orEmpty())
        }
    }
    private val titleChangedListener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            viewModel.updateTitle(s?.toString().orEmpty())
        }
    }

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
        val inputData = args?.getParcelable<NoteData>(NOTE_DATA)
        viewModel = NoteViewModel(requireContext())
        if (inputData != null) {
            viewModel.initData(inputData)
        }
        subscribeToViewModel()
        binding.buttonShare.setOnClickListener {
            viewModel.share()
        }
        //binding.title.addTextChangedListener { viewModel.updateTitle(it?.toString().orEmpty()) }
        //binding.content.addTextChangedListener { viewModel.updateText(it?.toString().orEmpty()) }
    }

    override fun onResume() {
        super.onResume()
        (activity as? NotePagerActivity)?.currentFragment = this
    }

    private fun onSaveSuccess() {
        showNotification(R.string.save_msg)
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
        binding.content.removeTextChangedListener(textChangedListener)
        binding.title.removeTextChangedListener(titleChangedListener)
        binding.title.setText(noteData?.title)
        binding.content.setText(noteData?.text)
        binding.content.addTextChangedListener(textChangedListener)
        binding.title.addTextChangedListener(titleChangedListener)
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
            onSaveSuccess()
        }
    }

    companion object {
        private const val NOTE_DATA: String = "Данные"

        fun newInstance(noteData: NoteData): NoteFragment = NoteFragment().apply {
            arguments = Bundle().apply { putParcelable(NOTE_DATA, noteData) }
        }
    }
}