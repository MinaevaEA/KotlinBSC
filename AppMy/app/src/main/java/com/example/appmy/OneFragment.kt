package com.example.appmy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.appmy.databinding.FragmentOneBinding


class OneFragment : Fragment() {
    private lateinit var binding: FragmentOneBinding
    private lateinit var fragment2: TwoFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View = FragmentOneBinding.inflate(inflater, container, false).also {
        binding = it
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment2 = TwoFragment()

        binding.button.setOnClickListener {
            val t = activity?.supportFragmentManager?.beginTransaction()
            t?.addToBackStack(null)
            t?.replace(R.id.activity_main, fragment2)
            t?.commit()
        }
    }

}

