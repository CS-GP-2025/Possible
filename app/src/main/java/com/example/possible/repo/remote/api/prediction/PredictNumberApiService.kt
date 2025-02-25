package com.example.possible.repo.remote.api.prediction

import com.example.possible.repo.remote.response.lettersNumbers.NumberApiResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PredictNumberApiService {
    @Multipart
    @POST("predictt")
    suspend fun uploadNumberImage(
        @Part image: MultipartBody.Part
    ): Response<NumberApiResponse>
}