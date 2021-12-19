package com.example.test1.list

import android.content.Context
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import kotlinx.coroutines.flow.Flow

class ListPresenter(private val mainView: ListView, context: Context) {
    private var notes: List<NoteData> = listOf()
    val database = NoteDatabase.getDatabase(context)

    fun loadAllNotes() {
        mainView.showNoteList(loadNotes())
    }

    private fun loadNotes(): Flow<List<NoteData>> {
        return database.noteDao().getAll()
    }

    fun btnAboutActivityClick() {
        mainView.openActivityAbout()
    }

    fun openNote(notes: List<NoteData>, currentPosition: Int) {
        mainView.onNoteOpen(notes, currentPosition)
    }

    fun createNote() {
        val newNote = NoteData(0, "", "")
        val newNoteList = mutableListOf<NoteData>().apply {
            addAll(notes)
            add(newNote)
        }
        notes = newNoteList
        openNote(newNoteList, newNoteList.size - 1)
    }
}
