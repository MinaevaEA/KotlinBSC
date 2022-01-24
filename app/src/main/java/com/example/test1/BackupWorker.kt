package com.example.test1

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.test1.database.ConcreteNoteDatabase
import kotlinx.coroutines.flow.collect

class BackupWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    private val database by lazy { ConcreteNoteDatabase.getDatabase(context) }

    override suspend fun doWork(): Result {

        database.noteDao().getAll().collect {
            println("количество заметок: ${it.size}")
        }
        return Result.success()

    }
}
