package com.example.possible.repo.remote
    import com.example.possible.repo.remote.api.prediction.PredictLetterApiService
    import com.example.possible.repo.remote.api.prediction.PredictNumberApiService
    import com.example.possible.repo.remote.response.lettersNumbers.LetterApiResponse
    import com.example.possible.repo.remote.response.lettersNumbers.LetterResponseDeserializer
    import com.google.gson.Gson
    import com.google.gson.GsonBuilder
    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory

    object RetrofitBuilder {



        // predict Api data
        //url
            private const val PREDICT_BASE_URL :String ="http://192.168.1.2:5000/"
        //code to select the retrofit
            private const val LETTER_CODE :Int = 1
            private const val NUMBER_CODE : Int = 2
        //retrofit for predict api
            private var letterPredictionRetrofit : Retrofit? = null
            private var numberPredictionRetrofit : Retrofit? = null
        //gson for predict api
            private val gsonLetterPrediction: Gson = GsonBuilder()
            .registerTypeAdapter(LetterApiResponse::class.java, LetterResponseDeserializer()).create()


        //register Api data








            private var retrofit : Retrofit? = null
            val gson = GsonBuilder().create()



        private fun getRetrofit(apiCode:Int,baseUrl:String,gson: Gson): Retrofit {

            var retrofit = when(apiCode){
               LETTER_CODE->{
                   letterPredictionRetrofit
                }
                NUMBER_CODE->{
                    numberPredictionRetrofit
                }
                else -> null
            }
            if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            }
            return retrofit!!
            }



        val letterApiService: PredictLetterApiService = getRetrofit(
            LETTER_CODE
            ,PREDICT_BASE_URL
            ,gsonLetterPrediction
        ).create(PredictLetterApiService::class.java)

        val numberApiService: PredictNumberApiService = getRetrofit(
            NUMBER_CODE
            ,PREDICT_BASE_URL
            ,gson
        ).create(PredictNumberApiService::class.java)

}