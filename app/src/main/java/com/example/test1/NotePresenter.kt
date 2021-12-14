package com.example.test1

import com.example.test1.database.NoteData


class NotePresenter(private val noteView: NoteView, private val noteData: NoteData?) {

     fun shareNote() {

         if (noteData?.text.isNullOrEmpty()) {
             noteView.onNoteEmpty()
         } else {
            // noteView.shareNote(noteData)
         }
     }

     init {
         noteView.showNote(noteData)
     }


}