package com.example.test1.note

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.test1.R
import com.example.test1.pager.NotePagerActivity

class DialogNoteFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = AlertDialog.Builder(it,R.style.Dialog)
            builder
                .setMessage(R.string.dialog_msg)
                .setNegativeButton(R.string.exit) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(R.string.buttonSave) { _, _ ->
                    (it as? NotePagerActivity)?.continueSave()
                }
                .create()
        }
    }
}