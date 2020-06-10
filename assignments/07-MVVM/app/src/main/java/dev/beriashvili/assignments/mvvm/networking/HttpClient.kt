package dev.beriashvili.assignments.mvvm.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

const val DOMAIN = "https://www.mocky.io"
const val API_VERSION = "v2"
const val BASE_URL = "$DOMAIN/$API_VERSION/"

object HttpClient {
    private var retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private var service = retrofit.create(
        ApiService::class.java
    )

    fun get(url: String, requestCallback: RequestCallback) {
        val call = service.get(url)

        call.enqueue(callback(requestCallback))
    }

    fun get(url: String, parameters: Map<String, String>, requestCallback: RequestCallback) {
        val call = service.get(url, parameters)

        call.enqueue(callback(requestCallback))
    }

    private fun callback(requestCallback: RequestCallback) = object : Callback<String> {
        override fun onFailure(call: Call<String>, throwable: Throwable) {
            requestCallback.onError(throwable)
        }

        override fun onResponse(call: Call<String>, response: Response<String>) {
            requestCallback.onSuccess(response.body().toString())
        }
    }
}
