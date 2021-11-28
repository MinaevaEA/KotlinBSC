package com.example.test1

interface MainUi {

    fun showNotification(message: String)
    fun onSaveSuccess()
    fun onSaveFailed()
    fun shareNote(title: String, message: String)
    fun startIntent()


}