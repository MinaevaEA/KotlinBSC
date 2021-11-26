package com.example.test1

import androidx.core.content.ContextCompat.startActivity


class MainPresenter(private val mainUi: MainUi) {
    fun onNewTodo(title: String, message: String) {
        if (title.isEmpty() && message.isEmpty()) {
            mainUi.onSaveFailed()
        } else {
            mainUi.onSaveSuccsess()
        }
    }

    fun shareBtnClick(title: String, message: String){
        if(message.isEmpty()){
            mainUi.onSaveFailed()
        }else{
            mainUi.shareNote(title,message)
        }
    }
    fun activityBtnClick(){
        mainUi.startIntent()

    }



}
