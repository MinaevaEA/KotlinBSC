package com.example.test1

class MainPresenter(private val mainUi: MainUi) {
    fun onNewTodo(title: String, message: String) {
        if (title.isEmpty() && message.length>0) {
            mainUi.onSaveFailed()
        }
        if ( message.isEmpty() && title.length>0){
            mainUi.onSaveFailed()
        }
            if(title.isEmpty() && message.isEmpty()){
                mainUi.textNull()
            }
            if(message.length>0 && title.length>0) {
            mainUi.onSaveSuccsess()
        }

      //  mainUi.showNotification(result)
    }
}
