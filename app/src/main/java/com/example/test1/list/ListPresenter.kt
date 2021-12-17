package com.example.test1.list

import android.content.Context
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDataBase
import kotlinx.coroutines.flow.Flow

class ListPresenter(private val mainView: ListMainView, context: Context) {
    var notes: List<NoteData> = listOf()
    val database = NoteDataBase.getDatabase(context)

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
}
