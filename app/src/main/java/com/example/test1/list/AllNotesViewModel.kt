package com.example.test1.list

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test1.NoteInteractor
import com.example.test1.SingleLiveEvent
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllNotesViewModel(
    private val database: NoteDatabase
) : ViewModel() {
    val notes = MutableLiveData<List<NoteData>>()
    val openNote = SingleLiveEvent<Pair<List<NoteData>, Int>>()
    val openAbout = SingleLiveEvent<Unit>()

    fun loadAllNotes() {
        viewModelScope.launch {
            loadNotes().collect {
                notes.postValue(it)
            }
        }
    }

    fun btnAboutActivityClick() {
        openAbout.call()
    }

    fun openNote(notes: List<NoteData>, currentPosition: Int) {
        openNote.postValue(notes to currentPosition)
    }

    fun createNote() {
        val newNote = NoteData(0, "", "")
        val newNoteList = mutableListOf<NoteData>().apply {
            addAll(notes.value ?: listOf())
            add(newNote)
        }
        notes.postValue(newNoteList)
        openNote(newNoteList, newNoteList.size - 1)
    }

    fun downloadNote() {
        NoteInteractor().getNote().enqueue(object : Callback<NoteData> {
            override fun onResponse(call: Call<NoteData>, response: Response<NoteData>) {
                val newNote = NoteData(
                    response.body()?.id ?: 0,
                    response.body()?.title ?: "",
                    response.body()?.text ?: ""
                )
                val newNoteList = mutableListOf<NoteData>().apply {
                    addAll(notes.value ?: listOf())
                    add(newNote)
                }
                notes.postValue(newNoteList)
                openNote(newNoteList, newNoteList.size - 1)
                Log.d(ContentValues.TAG, "+")
            }

            override fun onFailure(call: Call<NoteData>, t: Throwable?) {
                Log.d(ContentValues.TAG, "error")
            }
        })
    }

    private fun loadNotes(): Flow<List<NoteData>> = database.noteDao().getAll()
}

@Suppress("UNCHECKED_CAST")
class AllNotesViewModelFactory(
    private val database: NoteDatabase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AllNotesViewModel(database) as T
}
