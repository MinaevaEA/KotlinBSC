package com.example.test1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteData::class], version = 1)
abstract class ConcreteNoteDatabase : RoomDatabase(), NoteDatabase {
    abstract override fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ConcreteNoteDatabase::class.java,
                    "database"
                ).build()

                INSTANCE = instance
                instance
            }
    }
}

interface NoteDatabase {
    fun noteDao(): NoteDao
}
