package com.example.test1.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")

data class NoteData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "note_title") val title: String,
    @ColumnInfo(name = "note_text") val text: String
) : Serializable

fun NoteData.isEmpty() = title.isEmpty() && text.isEmpty()
