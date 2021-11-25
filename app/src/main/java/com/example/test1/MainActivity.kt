package com.example.test1

import android.content.Intent
import android.icu.text.CaseMap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
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
        findViewById<Button>(R.id.button1)
            .setOnClickListener {
                startIntent()
            }
        findViewById<Button>(R.id.button2)
            .setOnClickListener {
                shareNote(
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

    override fun showNotification(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT)

            .apply {
                setGravity(Gravity.TOP, 0, 250)
                show()
            }

    }

    private fun startIntent() {
        val intent = Intent(this, DataActivity::class.java)
        startActivity(intent)
    }

    private fun shareNote(title: String, message: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "$title/$message")

        }
        startActivity(shareIntent)

    }
}

//private fun broser(){
//    val browseIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
//    startActivity(browseIntent)
//}









