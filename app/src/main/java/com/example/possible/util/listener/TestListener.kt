package com.example.possible.util.listener

import com.example.possible.model.Test

interface TestListener {
    fun onTestClick(test: Test, position: Int)
    fun onDeleteClick(test: Test, position: Int)
}