package com.example.test1


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test1.list.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.activity_main, ListFragment()).commit()
    }
}







