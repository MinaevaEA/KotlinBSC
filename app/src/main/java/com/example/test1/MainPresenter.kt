package com.example.test1

class MainPresenter(private val mainUi: MainUi) {
    fun onNewTodo(title: String, message: String) {
        if (title.isEmpty() && message.isEmpty()) {
            mainUi.onSaveFailed()
        } else {
            mainUi.onSaveSuccsess()
        }

        //  mainUi.showNotification(result)
    }
}
