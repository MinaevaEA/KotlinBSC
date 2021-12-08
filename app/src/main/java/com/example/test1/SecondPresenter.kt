package com.example.test1


class SecondPresenter(private val fragmentUi: SecondFragmentUI) {
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