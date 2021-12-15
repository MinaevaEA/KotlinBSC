package com.example.test1.note

import android.content.Context
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDataBase


class NotePresenter(private val noteView: NoteView, private var noteData: NoteData?,context: Context) {
//тут проверка на
val database = NoteDataBase.getDatabase(context)
     fun shareNote() {

         if (noteData?.text.isNullOrEmpty()) {
             noteView.onNoteEmpty()
         } else {
            // noteView.shareNote(noteData)
         }
     }

   suspend fun save() {
       noteData?.also  {

            if (it.id > 0) {
                database.noteDao().update(it)
            }else{
                database.noteDao().insert(it).also {
                    updateId(it)
                }
            }
        }
    }

    fun updateTitle(title: String) {
        noteData?.also {
            noteData = it.copy(title = title)
        }
    }

    fun updateText(text: String) {
        noteData?.also {
            noteData = it.copy(text = text)
        }
    }
    fun updateId(id: Long) {
        noteData?.also {
            noteData = it.copy(id = id)
        }
    }

    init {
         noteView.showNote(noteData)
     }


}