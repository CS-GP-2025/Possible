package com.example.possible.util.listener

import com.example.possible.model.Letter

interface LettersListener {
    fun OnClick(letter: Letter, position: Int)
}