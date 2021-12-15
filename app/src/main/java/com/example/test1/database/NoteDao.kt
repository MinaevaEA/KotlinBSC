package com.example.test1.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<NoteData>>

    @Query("SELECT * FROM notes WHERE note_title LIKE :title LIMIT 1")
     fun findByTitleName(title: String): NoteData

    @Insert
     fun insert(note: NoteData): Long

    @Update
     fun update(note: NoteData)

    @Delete
     fun delete(note: NoteData)
}