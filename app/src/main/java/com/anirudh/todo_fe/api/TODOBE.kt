package com.anirudh.todo_fe.api

import com.anirudh.todo_fe.dataclass.*
import retrofit2.Response
import retrofit2.http.*

interface TODOBE {
    @POST("/users/")
    suspend fun create_user(@Body user:UserCreate):Response<UserCreateResponse>


    @FormUrlEncoded
    @POST("/login/")
    suspend fun login_user(
        @Field("username") username:String,
        @Field("password") password:String
    ):Response<Login>

    @GET("/list/")
    suspend fun get_task(@Header("Authorization")token:String):Response<TaskList>

    @POST("/list/")
    suspend fun create_task(@Header("Authorization")token:String,@Body task:Newtask):Response<TaskCreateResponse>

    @PUT("/list/{id}")
    suspend fun edit_task(@Header("Authorization") token:String,@Body task:Newtask,@Path("id")id:Int):Response<EditTaskResponse>
}