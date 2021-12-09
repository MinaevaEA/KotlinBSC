package com.example.test1


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class NoteFragment : Fragment(), NoteView {

    private lateinit var presenter: NotePresenter
    private lateinit var title: EditText
    private lateinit var content: EditText
    private lateinit var btnSave: Button
    private lateinit var btnShare: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_note, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments
        val inputData = args?.getParcelable<NoteData>(NOTE_DATA)
        title = view.findViewById(R.id.title)
        content = view.findViewById(R.id.content)
        btnSave = view.findViewById(R.id.buttonSave)
        btnShare = view.findViewById(R.id.buttonShare)
        presenter = NotePresenter(this, inputData)

        btnSave.setOnClickListener {
            presenter.onNoteSave((NoteData(title.text.toString(), content.text.toString())))
        }
        btnShare.setOnClickListener {
            presenter.btnShareClick(
                (NoteData(title.text.toString(), content.text.toString()))
            )
        }
    }

    companion object {

        private const val NOTE_DATA: String = "Данные"

        fun newInstance(noteData: NoteData): NoteFragment = NoteFragment().apply {
            arguments = Bundle().apply { putParcelable(NOTE_DATA, noteData) }
        }
    }

    override fun onSaveSuccess() {
        showNotification(R.string.save_msg)
    }

    override fun onNoteEmpty() {
        showNotification(R.string.msg_error)
    }

    private fun showNotification(msg_toast: Int) {
        Toast.makeText(requireContext(), msg_toast, Toast.LENGTH_SHORT)
            .show()
    }

    override fun shareNote(title: String, message: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "$title/$message")
        }
        startActivity(shareIntent)
    }

    override fun showNote(noteData: NoteData?) {

        title.setText(noteData?.title)
        content.setText(noteData?.text)
    }
}