package com.example.test1.note

import android.content.Context
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import com.example.test1.database.isEmpty

class NotePresenter(
    private val noteView: NoteView,
    private var noteData: NoteData?,
    context: Context
) {

    private val database = NoteDatabase.getDatabase(context)

    init {
        noteView.showNote(noteData)
    }

    fun share() {
        noteData?.let {
            if (it.isEmpty()) {
                noteView.onNoteEmpty()
            } else {
                noteView.share(it)
            }
        }
    }

    suspend fun save() {
        noteData?.also {
            if (it.isEmpty()) {
                noteView.onNoteEmpty()
            } else {
                if (it.id > 0) {
                    database.noteDao().update(it)
                } else {
                    database.noteDao().insert(it).also { newNoteId ->
                        updateId(newNoteId)
                    }
                }
                noteView.onSaveSuccess()
            }
        }
    }

    fun updateTitle(title: String) {
        noteData?.also {
            noteData = it.copy(title = title)
        }
    }

    fun updateText(text: String) {
        noteData?.also {
            noteData = it.copy(text = text)
        }
    }

    private fun updateId(id: Long) {
        noteData?.also {
            noteData = it.copy(id = id)
        }
    }

}

