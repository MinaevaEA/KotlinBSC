package com.example.test1.note

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import com.example.test1.database.isEmpty

class NoteViewModel(context: Context) : ViewModel() {
    val noteData = MutableLiveData<NoteData>()
    private val database = NoteDatabase.getDatabase(context)
    val noteShare = MutableLiveData<NoteData>()
    val noteEmpty = MutableLiveData<Unit>()
    val saveSuccess = MutableLiveData<NoteData>()
    fun initData(noteData: NoteData) {
        this.noteData.postValue(noteData)
    }

    fun share() {
        noteData.value?.let {
            if (it.isEmpty()) {
                this.noteEmpty.postValue(Unit)
            } else {
                this.noteShare.postValue(it)
            }
        }
    }

    suspend fun save() {
        noteData.value?.also {
            if (it.isEmpty()) {
                this.noteEmpty.postValue(Unit)
            } else {
                if (it.id > 0) {
                    database.noteDao().update(it)
                } else {
                    database.noteDao().insert(it).also { newNoteId ->
                        updateId(newNoteId)
                    }
                }
                this.saveSuccess.postValue(it)
            }
        }
    }

    fun updateTitle(title: String) {
        noteData.value?.also {
            noteData.value = it.copy(title = title)
        }
    }

    fun updateText(text: String) {
        noteData.value?.also {
            noteData.value = it.copy(text = text)
        }
    }

    private fun updateId(id: Long) {
        noteData.value?.also {
            noteData.value = it.copy(id = id)
        }
    }
}


