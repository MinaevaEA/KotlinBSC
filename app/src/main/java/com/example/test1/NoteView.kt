package com.example.test1

interface NoteView {

    fun onNoteEmpty()
    fun onSaveSuccess()
    fun shareNote(noteData: NoteData)
    fun showNote(noteData: NoteData?)
}