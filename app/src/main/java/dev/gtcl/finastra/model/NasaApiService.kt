package dev.gtcl.finastra.model

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/"
private const val API_KEY = "h7naY88ZNIeLMYzqcgfD5yd4PcGEcyqI3MKsPArM"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor{ message -> Log.d("API", message) }.apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface NasaApiService {

    @GET("mars-photos/api/v1/rovers/curiosity/photos/")
    fun getMarsRoverPhotos(
        @Query("sol")
        sol: Int,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Deferred<MarsRoverResponse>

}

object NasaApi {
    val retrofitService : NasaApiService by lazy { retrofit.create(NasaApiService::class.java) }
}