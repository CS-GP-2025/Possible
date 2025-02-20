package com.example.possible.ui.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.possible.databinding.ActivityTestBinding
import com.example.possible.databinding.ActivityTestResultBinding
import com.example.possible.model.Test
import com.example.possible.util.adapter.FragmentAdapter

class TestResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)
    }}