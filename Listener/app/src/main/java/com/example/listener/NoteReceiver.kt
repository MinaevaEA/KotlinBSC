package com.example.listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.TextView


class NoteReceiver(private var notes: TextView) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val newNote = intent?.getStringExtra(NOTE_TEXT)
        val notesLog = notes.text.toString()
        val result = "$newNote;\n$notesLog"
        notes.text = result
    }

    companion object {
        private const val NOTE_TEXT = "note_text"

    }
}