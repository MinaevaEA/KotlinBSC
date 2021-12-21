package com.example.test1.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class NoteData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "note_title") val title: String,
    @ColumnInfo(name = "note_text") val text: String
) : Parcelable

fun NoteData.isEmpty() = title.isEmpty() && text.isEmpty()
