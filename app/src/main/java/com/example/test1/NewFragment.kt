package com.example.test1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import java.lang.reflect.Array.newInstance
import kotlin.concurrent.fixedRateTimer


class NewFragment : Fragment() {
    private lateinit var title: EditText
    private lateinit var message: EditText
    private lateinit var btnToast: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_new, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments
        val inputTitle = args?.getString(SAMPLE_STRING_TITLE)
        val inputSub = args?.getString(SAMPLE_STRING_ARG)
        title = view.findViewById(R.id.title)
        message = view.findViewById(R.id.message)
        btnToast = view.findViewById(R.id.buttonToast)

        title.setText(inputTitle)
        message.setText(inputSub)
    }

    companion object {
        private const val SAMPLE_STRING_ARG: String = "Текст"
        private const val SAMPLE_STRING_TITLE: String ="Заголовок"

        fun newInstance(title: String, subtitle: String): NewFragment = NewFragment().apply {
            arguments = bundleOf(
                SAMPLE_STRING_TITLE to title,
                SAMPLE_STRING_ARG to subtitle,
            )
        }
    }




}