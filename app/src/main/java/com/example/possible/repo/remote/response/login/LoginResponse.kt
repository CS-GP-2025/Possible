package com.example.possible.repo.remote.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
