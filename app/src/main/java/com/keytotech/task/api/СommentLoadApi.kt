package com.keytotech.task.api

import android.util.Log
import com.keytotech.task.data.Comment
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentLoadApiClient {
    @GET("/comments")
    fun loadComment(@Query("id") id:Int) : Single<List<Comment>>
}

interface CommentLoadAdi{
    fun loadComment(id:Int) : Single<List<Comment>>
}

class CommentAdiImpl : CommentLoadAdi{

    private val apiClient: CommentLoadApiClient
    companion object {
        private const val BASE_URL = "http://jsonplaceholder.typicode.com"
    }
    init {
      //  val logInterceptor = HttpLoggingInterceptor { message -> Log.w("Retrofit", message) }
       // logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
           //         .addInterceptor(logInterceptor)
                    .build())
            .baseUrl(BASE_URL)
            .build()

        apiClient = retrofit.create(CommentLoadApiClient::class.java)
    }

    override fun loadComment(id: Int): Single<List<Comment>> {
       return apiClient.loadComment(id)
    }
}