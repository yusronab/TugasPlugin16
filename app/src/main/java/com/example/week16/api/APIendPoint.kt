package com.example.week16.api

import com.example.week16.model.Posts
import retrofit2.Call
import retrofit2.http.*

interface APIendPoint {

    @GET("person")
    fun getAllPosts(): Call<ListResponse<Posts>>

    @GET("person/{id}")
    fun getPostsById(@Path("id")id: Int): Call<SingleResponse<Posts>>

    @DELETE("person/{id}")
    fun delete(@Path("id")id: Int): Call<Void>

    @FormUrlEncoded
    @POST("person")
    fun addPost(
            @Field("first_name") first_name: String,
            @Field("last_name") last_name: String,
            @Field("email") email: String
    ): Call<ListResponse<Posts>>

    @FormUrlEncoded
    @PUT("person/{id}")
    fun updatePost(
            @Path("id") id: Int,
            @Field("first_name") first_name: String,
            @Field("last_name") last_name: String,
            @Field("email") email: String
    ): Call<SingleResponse<Posts>>
}

data class ListResponse<T>(
        var message: String,
        var status: Int,
        var data: List<T>
)

data class SingleResponse<T>(
        var message: String,
        var status: Int,
        var data: T
)