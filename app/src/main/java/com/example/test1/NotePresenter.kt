package com.example.test1

class NotePresenter(private val fragmentUi: NoteFragmentView) {
    fun onNewTodo(title: String, message: String) {
        if (title.isEmpty() && message.isEmpty()) {
            fragmentUi.onSaveFailed()
        } else {
            fragmentUi.onSaveSuccess()
        }
    }

    fun shareBtnClick(title: String, message: String) {
        if (message.isEmpty()) {
            fragmentUi.onSaveFailed()
        } else {
            fragmentUi.shareNote(title, message)
        }
    }
}