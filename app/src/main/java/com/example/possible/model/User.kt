package com.example.possible.model

class User(
    private var name: String,
    private var login:Boolean,
    private var avatarUrl: String,
    private var email: String,
    private var password: String
) {
    fun getName(): String {
        return name
    }
    fun getEmail(): String{
        return email
    }
    fun getPassword(): String{
        return password
    }
}