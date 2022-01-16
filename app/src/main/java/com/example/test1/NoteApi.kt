package com.example.test1

import com.example.test1.database.NoteData
import retrofit2.Call
import retrofit2.http.GET

interface NoteApi {
    @GET("note.json?alt=media")
    fun getNote(): Call<NoteData>
}