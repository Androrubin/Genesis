package com.androrubin.genesis.Authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androrubin.genesis.R
import com.androrubin.genesis.databinding.ActivityMainBinding

class MainActivity_2 : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main_2)
    }
}