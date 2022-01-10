package com.example.test1.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test1.SingleLiveEvent
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import com.example.test1.database.isEmpty
import kotlinx.coroutines.launch

class NoteViewModel(private val database: NoteDatabase) : ViewModel() {
    val noteData = MutableLiveData<NoteData>()
    val noteShare = SingleLiveEvent<NoteData>()
    val noteEmpty = SingleLiveEvent<Unit>()
    val saveSuccess = SingleLiveEvent<NoteData>()

    fun initData(noteData: NoteData) {
        if (this.noteData.value == null) {
            this.noteData.postValue(noteData)
        }
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
            noteData.value = it.copy(title = title).apply {
                viewModelScope.launch {
                    database.noteDao().update(it)
                }
            }
        }
    }

    fun updateText(text: String) {
        noteData.value?.also {
            noteData.value = it.copy(text = text).apply {
                viewModelScope.launch {
                    database.noteDao().update(it)
                }
            }
        }
    }

    private fun updateId(id: Long) {
        noteData.value?.also {
            noteData.value = it.copy(id = id).apply {
                viewModelScope.launch {
                    database.noteDao().update(it)

                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(
    private val database: NoteDatabase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(database) as T
    }
}

