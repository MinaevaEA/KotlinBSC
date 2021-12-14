package com.example.test1

import com.example.test1.database.NoteData
import kotlinx.coroutines.flow.Flow

interface ListMainView {
    fun openActivityAbout()
    fun showNoteList(notes: Flow<List<NoteData>>)
    fun onNoteOpen(notes: List<NoteData>, currentPosition: Int)

}