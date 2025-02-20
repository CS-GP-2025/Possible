package com.example.possible.model

data class Test(
    val name: String,
    val type: String,
    var listOfQuestions: List<Question>
)
data class Question (
    val type: String,
    val question: String,
    val level : String = "Beginner",
)