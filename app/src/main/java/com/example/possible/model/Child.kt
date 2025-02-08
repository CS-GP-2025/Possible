package com.example.possible.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "children")
data class Child(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val age: Int,
    val imageUri: String,
    val gender: String,
    val disease: String,
    val difficulty: String
)
