package dev.beriashvili.assignments.userfragments

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

const val BASE_URL = "https://reqres.in/api/"

object HttpClient {
    private var retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private var service = retrofit.create(Service::class.java)

    fun get(path: String, requestCallback: RequestCallback) {
        val call = service.get(path)

        call.enqueue(callback(requestCallback))
    }

    fun get(root: String, path: String, requestCallback: RequestCallback) {
        val call = service.get(root, path)

        call.enqueue(callback(requestCallback))
    }

    fun get(
        path: String,
        options: Map<String, String>,
        requestCallback: RequestCallback
    ) {
        val call = service.get(path, options)

        call.enqueue(callback(requestCallback))
    }

    fun post(
        path: String,
        options: MutableMap<String, String>,
        requestCallback: RequestCallback
    ) {
        val call = service.post(path, options)

        call.enqueue(callback(requestCallback))
    }

    private fun callback(requestCallback: RequestCallback) = object : Callback<String> {
        override fun onFailure(call: Call<String>, t: Throwable) {
            requestCallback.onFailed(t.message.toString())
        }

        override fun onResponse(call: Call<String>, response: Response<String>) {
            requestCallback.onSuccess(response.body().toString())
        }
    }
}

interface Service {
    @GET("{path}")
    fun get(@Path("path") path: String): Call<String>

    @GET("{root}/{path}")
    fun get(@Path("root") root: String, @Path("path") path: String): Call<String>

    @GET("{path}")
    fun get(
        @Path("path") path: String,
        @QueryMap options: Map<String, String>
    ): Call<String>

    @FormUrlEncoded
    @POST("{path}")
    fun post(
        @Path("path") path: String,
        @QueryMap options: Map<String, String>
    ): Call<String>
}
