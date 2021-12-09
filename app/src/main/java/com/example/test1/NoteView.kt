package com.example.test1

interface NoteView {

    fun onNoteEmpty()
    fun onSaveSuccess()
    fun shareNote(title: String, message: String)
    fun showNote(noteData: NoteData?)

}