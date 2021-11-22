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

    override fun onSaveSuccsess() {
        showNotification(getString(R.string.sucsess_msg))
    }

    override fun onSaveFailed() {
        showNotification(getString(R.string.msgerror))
    }

    override fun textNull() {
        showNotification(getString(R.string.nomsg))
    }

    private fun showNotification(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT)
            .apply {
                setGravity(Gravity.TOP, 0, 250)
                show()
            }

    }
}
