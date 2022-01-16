package com.example.test1


import com.example.test1.database.NoteData
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteInteractor {
    fun getNote(): Call<NoteData> = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build().create(NoteApi::class.java).getNote()

    companion object {
        private const val BASE_URL =
            "https://firebasestorage.googleapis.com/v0/b/notes-bbfaa.appspot.com/o/"
    }
}