package com.example.test1

class NotePresenter(private val fragmentUi: NoteFragmentView) {
    fun onNewSave(title: String, message: String) {
        if (title.isEmpty() && message.isEmpty()) {
            fragmentUi.onNoteEmpty()
        } else {
            fragmentUi.onSaveSuccess()
        }
    }

    fun shareBtnClick(title: String, message: String) {
        if (message.isEmpty()) {
            fragmentUi.onNoteEmpty()
        } else {
            fragmentUi.shareNote(title, message)
        }
    }
}