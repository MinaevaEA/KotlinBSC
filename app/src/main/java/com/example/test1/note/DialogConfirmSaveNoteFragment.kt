package com.example.test1.note

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.test1.R
import com.example.test1.pager.NotePagerActivity
import java.lang.IllegalStateException

class DialogNoteFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage(R.string.dialog_msg)
                .setNegativeButton(R.string.exit) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(R.string.buttonSave) { _, _ ->
                    (activity as? NotePagerActivity)?.continueSave()
                }
                .create()
        } ?: throw IllegalStateException("Exception")
    }
}