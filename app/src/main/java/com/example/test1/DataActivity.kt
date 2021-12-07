package com.example.test1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_activity)
         supportFragmentManager.beginTransaction().replace(R.id.fragment_container,NoteFragment()).commit()


    }


        }













