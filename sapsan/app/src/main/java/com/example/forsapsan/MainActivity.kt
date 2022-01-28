package com.example.forsapsan

class MainActivity : SingleFragmentActivity() {
    override fun createFragment() = MainFragment.newInstance()
}