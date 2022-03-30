package com.anirudh.todo_fe.Repo


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anirudh.todo_fe.api.TODOBE
import com.anirudh.todo_fe.dataclass.*
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.util.function.LongFunction
import kotlin.coroutines.coroutineContext


class Repo(private val service:TODOBE){

    private val LoginResLivedata = MutableLiveData<Response<Login>>()
    private val UserResLivedata = MutableLiveData<Response<UserCreateResponse>>()
    private val TaskListLivedata = MutableLiveData<Response<TaskList>>()
    private val taskCreateLiveData = MutableLiveData<Response<TaskCreateResponse>>()
    private val taskEditLiveData = MutableLiveData<Response<EditTaskResponse>>()
    private val friendListLiveData = MutableLiveData<Response<FriendList>>()


    val loginlivedata:LiveData<Response<Login>>
    get() = LoginResLivedata

    val createlivedata:LiveData<Response<UserCreateResponse>>
    get() = UserResLivedata

    val tasklivedata:LiveData<Response<TaskList>>
    get() = TaskListLivedata

    val taskcreatelivedata:LiveData<Response<TaskCreateResponse>>
    get() = taskCreateLiveData

    val taskeditlivedata :LiveData<Response<EditTaskResponse>>
    get() = taskEditLiveData

    val friendlistlivedata:LiveData<Response<FriendList>>
    get() = friendListLiveData

    suspend fun UserLogin(username:String,password:String){
        try{
            val result = service.login_user(username,password)
            if(result.isSuccessful){
                LoginResLivedata.postValue(Response.Success(result.body()))
            }
            else{
                LoginResLivedata.postValue(Response.Error(result.body().toString()))
            }
        }catch (e:Exception){
            LoginResLivedata.postValue(Response.Error("Some Error"))
        }
        catch (e:SocketTimeoutException){
            LoginResLivedata.postValue(Response.Error(e.message.toString()))
        }
    }
    suspend fun CreateUser(username:String,email:String,password: String){
        try{
            val result = service.create_user(UserCreate(email,password,username))
            if(result.isSuccessful){
                UserResLivedata.postValue(Response.Success(result.body()))
            }
            else{
                UserResLivedata.postValue(Response.Error(result.body().toString()))
            }

        }catch (e:Exception){
            UserResLivedata.postValue(Response.Error(e.message.toString()))
        }
        catch (e:SocketTimeoutException){
            UserResLivedata.postValue(Response.Error(e.message.toString()))
        }

    }
    suspend fun getTask(token:String) {

        try {
            val result = service.get_task(token)

        if (result.isSuccessful) {
            TaskListLivedata.postValue(Response.Success(result.body()))
        } else {
            val error = result.message()
            TaskListLivedata.postValue(Response.Error(error))
        }

    }catch (e:SocketTimeoutException){
        TaskListLivedata.postValue(Response.Error(e.message.toString()))
        }
    }
    suspend fun create_task(token:String,title:String,content:String,is_Completed:Boolean){
        try{
            val result = service.create_task(token,Newtask(content,is_Completed, title))
            if(result.isSuccessful){
                taskCreateLiveData.postValue(Response.Success(result.body()))
            }
            else if (result.code() == 401){
                taskCreateLiveData.postValue(Response.Error(result.body().toString()))
            }
        }catch (e:HttpException) {
            taskCreateLiveData.postValue(Response.Error(e.message.toString()))
        }
        catch (e:SocketTimeoutException){
            taskCreateLiveData.postValue(Response.Error(e.message.toString()))
        }
    }

    suspend fun edit_task(token: String,task:Newtask,id:Int){
        try{
            val result = service.edit_task(token,task,id)
            if(result.isSuccessful){
                taskEditLiveData.postValue(Response.Success(result.body()))
            }else{
                val error = result.raw()
                taskEditLiveData.postValue(Response.Error(error.message))
            }

        }catch (e:SocketTimeoutException){
            taskEditLiveData.postValue(Response.Error(e.message.toString()))
        }
        catch (e:HttpException){
            taskEditLiveData.postValue(Response.Error(e.message().toString()))
        }
    }
    suspend fun friend_list(user_key:String){
        try{
            val result = service.get_friend_list(user_key)
            if(result.isSuccessful){
                friendListLiveData.postValue(Response.Success(result.body()))
            }
            else{
                val error = result.raw()
                friendListLiveData.postValue(Response.Error(error.message))
            }
        }catch (e:SocketTimeoutException){
            friendListLiveData.postValue(Response.Error(e.message.toString()))
        }
        catch (e:HttpException){
            friendListLiveData.postValue(Response.Error(e.message.toString()))
        }
    }
}