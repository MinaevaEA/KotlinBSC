package com.example.test1

import android.content.Context
import android.content.Intent

class NotePresenter(private val noteView: NoteView, noteData: NoteData?) {


    fun saveNote(noteData: NoteData) {

        if (noteData.title.isEmpty() && noteData.text.isEmpty()) {
            noteView.onNoteEmpty()
        } else {
            noteView.onSaveSuccess()
        }
    }

    fun shareNote(noteData: NoteData) {

        if (noteData.text.isEmpty()) {
            noteView.onNoteEmpty()
        } else {
            noteView.shareNote(noteData)
        }
    }

    init {
        noteView.showNote(noteData)
    }


}