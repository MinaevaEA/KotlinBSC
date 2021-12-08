package com.example.test1


import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.core.os.bundleOf


class NoteFragment : Fragment(), NoteFragmentView {
    private lateinit var title: EditText
    private lateinit var message: EditText
    private lateinit var btnToast: Button
    private lateinit var btnShare: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_second, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val presenter = NotePresenter(this)
        val args = this.arguments
        val inputTitle = args?.getString(SAMPLE_STRING_TITLE)
        val inputSub = args?.getString(SAMPLE_STRING_ARG)

        title = view.findViewById(R.id.title)
        message = view.findViewById(R.id.message)
        btnToast = view.findViewById(R.id.buttonToast)
        btnShare = view.findViewById(R.id.buttonShare)
        title.setText(inputTitle)
        message.setText(inputSub)

        btnToast.setOnClickListener {
            presenter.onNewTodo(title.text.toString(), message.text.toString())
        }
        btnShare.setOnClickListener {
            presenter.shareBtnClick(
                title.text.toString(), message.text.toString()
            )
        }

    }

    companion object {
        private const val SAMPLE_STRING_ARG: String = "Текст"
        private const val SAMPLE_STRING_TITLE: String = "Заголовок"

        fun newInstance(title: String, subtitle: String): NoteFragment = NoteFragment().apply {
            arguments = bundleOf(
                SAMPLE_STRING_TITLE to title,
                SAMPLE_STRING_ARG to subtitle,
            )
        }
    }

    override fun onSaveSuccess() {
        showNotification(getString(R.string.save_msg))
    }

    override fun onSaveFailed() {
        showNotification(getString(R.string.msg_error))
    }

    private fun showNotification(msg_toast: String) {
        Toast.makeText(requireContext(), msg_toast, Toast.LENGTH_SHORT)
            .apply {
                setGravity(Gravity.TOP, 0, 250)
                show()
            }
    }

    override fun shareNote(title: String, message: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "$title/$message")

        }
        startActivity(shareIntent)
    }
}