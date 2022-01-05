package com.example.test1.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<NoteData>>

    @Insert
    suspend fun insert(note: NoteData): Long

    @Update
    suspend fun update(note: NoteData)

    @Delete
    suspend fun delete(note: NoteData)
}