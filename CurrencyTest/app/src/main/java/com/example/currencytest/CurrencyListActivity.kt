package com.example.currencytest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CurrencyListActivity : AppCompatActivity(), CurrencyView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterCurrency(DataSource.getCurrencyList(), this)
    }

    override fun openCurrency(dataCurrency: DataCurrency) {
        Log.d("11111", "11111")
        intent = Intent(this, CurrencyActivity::class.java)
        intent.putExtra("123", dataCurrency.title)
        startActivity(intent)
    }
}