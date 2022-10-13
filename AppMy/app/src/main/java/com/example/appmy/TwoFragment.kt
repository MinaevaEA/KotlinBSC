package com.example.appmy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appmy.databinding.FragmentOneBinding
import com.example.appmy.databinding.FragmentTwoBinding


class TwoFragment : Fragment() {
private lateinit var binding: FragmentTwoBinding
private lateinit var fragment1: OneFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTwoBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment1 = OneFragment()

        binding.button.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.activity_main, fragment1)
                ?.commit()
        }
    }

}