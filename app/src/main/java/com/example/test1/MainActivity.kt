package com.example.test1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test1.databinding.ActivityMainBinding
import com.example.test1.list.ListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main, ListFragment())
                .commit()
        }
    }
}







