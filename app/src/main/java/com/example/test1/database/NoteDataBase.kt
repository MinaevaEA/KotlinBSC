package com.example.test1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [NoteData::class], version = 1)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDataBase? = null
        fun getDatabase(context: Context): NoteDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}