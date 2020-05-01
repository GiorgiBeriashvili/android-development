package dev.beriashvili.assignments.userlist

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

object DataLoader {
    private var retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl("https://reqres.in/api/")
        .build()

    private var service = retrofit.create(ApiRetrofit::class.java)

    fun getRequest(path: String, customCallback: CustomCallback) {
        val call = service.getRequest(path)

        call.enqueue(callback(customCallback))
    }

    fun getRequest(root: String, path: String, customCallback: CustomCallback) {
        val call = service.getRequest(root, path)

        call.enqueue(callback(customCallback))
    }

    fun getRequest(path: String, parameters: Map<String, String>, customCallback: CustomCallback) {
        val call = service.getRequest(path, parameters)

        call.enqueue(callback(customCallback))
    }

    fun postRequest(
        path: String,
        parameters: MutableMap<String, String>,
        customCallback: CustomCallback
    ) {
        val call = service.postRequest(path, parameters)

        call.enqueue(callback(customCallback))
    }

    private fun callback(customCallback: CustomCallback) = object : Callback<String> {
        override fun onFailure(call: Call<String>, t: Throwable) {
            customCallback.onFailed(t.message.toString())
        }

        override fun onResponse(call: Call<String>, response: Response<String>) {
            customCallback.onSuccess(response.body().toString())
        }
    }
}

interface ApiRetrofit {
    @GET("{path}")
    fun getRequest(@Path("path") path: String): Call<String>

    @GET("{root}/{path}")
    fun getRequest(@Path("root") root: String, @Path("path") path: String): Call<String>

    @GET("{path}")
    fun getRequest(
        @Path("path") path: String,
        @QueryMap parameters: Map<String, String>
    ): Call<String>

    @FormUrlEncoded
    @POST("{path}")
    fun postRequest(
        @Path("path") path: String,
        @QueryMap parameters: Map<String, String>
    ): Call<String>
}