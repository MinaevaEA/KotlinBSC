package com.example.test1

import android.content.Context
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDataBase
import kotlinx.coroutines.flow.Flow

class ListPresenter(private val mainView: ListMainView, context: Context) {

    val database = NoteDataBase.getDatabase(context)

    fun loadAllNotes(){
        mainView.showNoteList(loadNotes())
    }
    private fun loadNotes(): Flow<List<NoteData>> {
        return database.noteDao().getAll()
    }

   // private var notes = mutableListOf<NoteData>()

    fun btnAboutActivityClick() {
        mainView.openActivityAbout()
    }

    fun openNote(notes: List<NoteData>, currentPosition: Int) {
        mainView.onNoteOpen(notes, currentPosition)
    }

  /*  init {
        for (i in 0 until 100) {
            val note = NoteData(title = String(), text = String())
            note.title = "Заголовок #$i"
            note.text = "Текст #$i"
            notes += note
        }
        mainView.showNoteList(notes)
    }*/

}
