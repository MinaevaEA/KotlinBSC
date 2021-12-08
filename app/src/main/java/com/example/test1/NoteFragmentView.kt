package com.example.test1

interface NoteFragmentView {
    fun onSaveFailed()
    fun onSaveSuccess()
    fun shareNote(title: String, message: String)
}