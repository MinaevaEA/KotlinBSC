package com.example.test1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity(), MainUi {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = MainPresenter(this)
        findViewById<Button>(R.id.button)
            .setOnClickListener {
                presenter.onNewTodo(
                    findViewById<EditText>(R.id.title).text.toString(),
                    findViewById<EditText>(R.id.message).text.toString()
                )
            }
    }

    override fun showNotification(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .apply {
                setGravity(Gravity.TOP, 0, 250)
                show()
            }
    }
}
