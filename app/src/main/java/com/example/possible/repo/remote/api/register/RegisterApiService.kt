package com.example.possible.repo.remote.api.register
import com.example.possible.repo.remote.response.login.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RegisterApiService {
    @Multipart
        @POST("Register") // حط المسار المناسب للـ API
        fun registerUser(
            @Part("username") username: RequestBody,
            @Part("email") email: RequestBody,
            @Part("password") password: RequestBody,
            @Part("RoleNo") roleNo: RequestBody,
            @Part image: MultipartBody.Part
        ): Call<RegisterResponse>
    }
