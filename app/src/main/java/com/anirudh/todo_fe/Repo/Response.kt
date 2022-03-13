package com.anirudh.todo_fe.Repo

import okhttp3.ResponseBody
import retrofit2.HttpException

sealed class Response<T>(val data:T? = null , val error:String? = null){
    class Loading<T>:Response<T>()
    class Success<T>(data:T? = null):Response<T>(data = data)
    class Error<T>(error: String):Response<T>(error = error)
}