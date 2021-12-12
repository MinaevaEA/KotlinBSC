package com.example.test1

interface ListMainView {
    fun openActivityAbout()
    fun showNoteList(notes: List<NoteData>)
    fun onNoteOpen(notes: List<NoteData>, currentPosition: Int)
}