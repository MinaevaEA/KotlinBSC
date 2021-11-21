package com.example.test1

class MainPresenter(private val mainUi: MainUi) {
    fun onNewTodo(title: String, message: String) {
        val result = if (title.isEmpty() && message.isEmpty()) {
            "Введите данные"
        } else {
            "Сохранено"
        }

        mainUi.showNotification(result)
    }
}
