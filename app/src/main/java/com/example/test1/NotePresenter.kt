package com.example.test1

class NotePresenter(private val fragmentView: NoteFragmentView, noteData: NoteData?) {
    fun onNewSave(title: String, message: String) {
        if (title.isEmpty() && message.isEmpty()) {
            fragmentView.onNoteEmpty()
        } else {
            fragmentView.onSaveSuccess()
        }
    }

    fun btnShareClick(title: String, message: String) {
        if (message.isEmpty()) {
            fragmentView.onNoteEmpty()
        } else {
            fragmentView.shareNote(title, message)
        }
    }

    init {
        fragmentView.showNote(noteData)
    }
}