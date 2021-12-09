package com.example.test1

class NotePresenter(private val noteView: NoteView, noteData: NoteData?) {

    fun onNoteSave(noteData: NoteData) {

        if (noteData.title.isEmpty() && noteData.text.isEmpty()) {
            noteView.onNoteEmpty()
        } else {
            noteView.onSaveSuccess()
        }
    }

    fun btnShareClick(noteData: NoteData) {

        if (noteData.text.isEmpty()) {
            noteView.onNoteEmpty()
        } else {
            noteView.shareNote(noteData.title, noteData.text)
        }
    }

    init {
        noteView.showNote(noteData)
    }
}