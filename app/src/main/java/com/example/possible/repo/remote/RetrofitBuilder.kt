package com.example.possible.repo.remote

import com.example.possible.repo.remote.api.aiPrediction.PredictLetterApiService
import com.example.possible.repo.remote.api.aiPrediction.PredictNumberApiService
import com.example.possible.repo.remote.api.account.LoginApiService
import com.example.possible.repo.remote.api.account.RegisterApiService
import com.example.possible.repo.remote.api.account.UpdateUserDataService
import com.example.possible.repo.remote.api.children.AddChild
import com.example.possible.repo.remote.api.children.DeleteChildApiService
import com.example.possible.repo.remote.api.children.GetAllChildren
import com.example.possible.repo.remote.api.children.GetUserChildren
import com.example.possible.repo.remote.api.children.UpdateChild
import com.example.possible.repo.remote.response.lettersNumbers.LetterApiResponse
import com.example.possible.repo.remote.response.lettersNumbers.LetterResponseDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val LOCALHOST_IP ="192.168.1.2"
    private const val LETTERS_BASE_URL = "http://$LOCALHOST_IP:5000/"
    private const val NUMBERS_BASE_URL = "http://$LOCALHOST_IP:3000/"
    private const val API_BASE_URL = "https://gp-api.runasp.net/api/"

    private val gsonLetterPrediction: Gson = GsonBuilder()
        .registerTypeAdapter(LetterApiResponse::class.java, LetterResponseDeserializer())
        .create()

    private val gsonDefault: Gson = GsonBuilder().create()
    private val gsonLenient: Gson = GsonBuilder().setLenient().create()

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(logging)
        .build()

    private fun createRetrofit(baseUrl: String, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    //ai prediction Api services

    val letterApiService: PredictLetterApiService by lazy {
        createRetrofit(LETTERS_BASE_URL, gsonLetterPrediction).create(PredictLetterApiService::class.java)
    }

    val numberApiService: PredictNumberApiService by lazy {
        createRetrofit(NUMBERS_BASE_URL, gsonDefault).create(PredictNumberApiService::class.java)
    }

    private val apiRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonLenient))
            .addConverterFactory(GsonConverterFactory.create(gsonDefault))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    //account Api services

    val registerApiService: RegisterApiService by lazy {
        apiRetrofit.create(RegisterApiService::class.java)
    }

    val loginApiService: LoginApiService by lazy {
        apiRetrofit.create(LoginApiService::class.java)
    }

    val editProfileApiService: UpdateUserDataService by lazy {
        apiRetrofit.create(UpdateUserDataService::class.java)
    }



    //children Api services

    val addChildApiService: AddChild by lazy {
        apiRetrofit.create(AddChild::class.java)
    }

    val deleteChildApiService: DeleteChildApiService by lazy {
        apiRetrofit.create(DeleteChildApiService::class.java)
    }
    val getUserChildrenApiService: GetUserChildren by lazy {
        apiRetrofit.create(GetUserChildren::class.java)
    }
    val updateChildApiService: UpdateChild by lazy {
        apiRetrofit.create(UpdateChild::class.java)
    }
    val getAllChildrenApiService: GetAllChildren by lazy {
        apiRetrofit.create(GetAllChildren::class.java)
    }
}
