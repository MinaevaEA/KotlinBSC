package com.example.test1.list

import com.example.test1.database.NoteData
import kotlinx.coroutines.flow.Flow

interface ListView {
    fun openActivityAbout()
    fun showNoteList(notes: Flow<List<NoteData>>)
    fun onNoteOpen(notes: List<NoteData>, currentPosition: Int)
}