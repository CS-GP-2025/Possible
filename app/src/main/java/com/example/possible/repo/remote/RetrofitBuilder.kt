package com.example.possible.repo.remote

import com.example.possible.repo.remote.api.prediction.PredictLetterApiService
import com.example.possible.repo.remote.api.prediction.PredictNumberApiService
import com.example.possible.repo.remote.api.register.LoginApiService
import com.example.possible.repo.remote.api.register.RegisterApiService
import com.example.possible.repo.remote.api.register.UpdateUserDataService
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

    // API URLs
    private const val LOCALHOST_IP ="192.168.1.2"
    private  var LETTERS_BASE_URL = "http://$LOCALHOST_IP:5000/"
    private var NUMBERS_BASE_URL = "http://$LOCALHOST_IP:3000/"
    private var REGISTER_BASE_URL = "https://gp-api.runasp.net/api/Account/"
    private var LOGIN_BASE_URL ="https://gp-api.runasp.net/api/Account/"
    private var EDIT_PROFILE_URL ="https://gp-api.runasp.net/api/Account/"

    // Gson instances
    private val gsonLetterPrediction: Gson = GsonBuilder()
        .registerTypeAdapter(LetterApiResponse::class.java, LetterResponseDeserializer())
        .create()

    private val gsonDefault: Gson = GsonBuilder().create()
//
// logging
   private val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    // OkHttpClient with timeouts
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // زيادة مدة الاتصال
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)  // إعادة المحاولة عند الفشل
        .addInterceptor(logging)
        .build()

    // Retrofit instance for letters API
    private val letterRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(LETTERS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonLetterPrediction))
            .client(okHttpClient)
            .build()
    }

    // Retrofit instance for numbers API
    private val numberRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NUMBERS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonDefault))
            .client(okHttpClient)
            .build()
    }
    val gsonLenient: Gson = GsonBuilder().setLenient().create()
    private val registerRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(REGISTER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonDefault))
            .client(okHttpClient)
            .build()
    }
    private val loginRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(LOGIN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonDefault))
            .client(okHttpClient)
            .build()
    }
    private val editProfileRetrofit: Retrofit by lazy {
        //val gson22 = GsonBuilder().setLenient().create()

        Retrofit.Builder()
            .baseUrl(EDIT_PROFILE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonLenient))
            .addConverterFactory(GsonConverterFactory.create(gsonDefault))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val registerApiService: RegisterApiService by lazy {
        registerRetrofit.create(RegisterApiService::class.java)
    }

    // API Services
    val letterApiService: PredictLetterApiService by lazy {
        letterRetrofit.create(PredictLetterApiService::class.java)
    }

    val numberApiService: PredictNumberApiService by lazy {
        numberRetrofit.create(PredictNumberApiService::class.java)
    }
    val loginApiService: LoginApiService by lazy {
        loginRetrofit.create(LoginApiService::class.java)
    }
    val editProfileApiService: UpdateUserDataService by lazy {
        editProfileRetrofit.create(UpdateUserDataService::class.java)
    }
}
