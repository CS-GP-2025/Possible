package com.example.possible.model

data class AllChildrenChildModel(
    val id: Int,
    val name: String,
    val age: Int,
    val gender: Int,
    val image: String,
    val parentId: String,
    val parent: Any?,
    val testChildrens: Any?
)
