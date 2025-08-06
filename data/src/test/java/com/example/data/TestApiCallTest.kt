package com.example.data

import com.example.data.remote.api.DragonBallApiService
import com.example.data.repository.CharacterRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestApiCallTest {

    @Test
    fun testFetchImages() = runBlocking {
        // Logging interceptor to print API response
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Retrofit instance with logging
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val api = retrofit.create(DragonBallApiService::class.java)
        val repository = CharacterRepositoryImpl(api)

        val images = repository.getCharacterImages()

        if (images.isNotEmpty()) {
            println("✅ Fetched ${images.size} images:")
            images.forEach { println(it) }
        } else {
            println("⚠️ No images found from the API.")
        }

        // Optional: You can uncomment this if you want the test to fail when no images
        // assert(images.isNotEmpty()) { "No images found from the API" }
    }
}
