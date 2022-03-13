package com.anirudh.todo_fe.MainViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anirudh.todo_fe.Repo.Repo
import com.anirudh.todo_fe.Repo.Response
import com.anirudh.todo_fe.dataclass.*
import kotlinx.coroutines.launch

class ViewModel(val repo: Repo):ViewModel(){
    fun userlogin(username:String,password:String) = viewModelScope.launch {
        repo.UserLogin(username,password)
    }

    fun createuser(username:String,email:String,password: String) = viewModelScope.launch {
        repo.CreateUser(username, email, password)
    }

    fun getTask(token:String) = viewModelScope.launch{
       repo.getTask(token)
    }

    fun createtask(token:String,title:String,content:String,is_completed:Boolean) = viewModelScope.launch {
        repo.create_task(token,title,content,is_completed)
    }

    fun edittask(token: String,task:Newtask,id:Int) = viewModelScope.launch {
        repo.edit_task(token,task,id)
    }

    val res_login:LiveData<Response<Login>>
    get() = repo.loginlivedata

    val res_create:LiveData<Response<UserCreateResponse>>
    get() = repo.createlivedata

    val res_task:LiveData<Response<TaskList>>
    get() = repo.tasklivedata

    val res_create_task:LiveData<Response<TaskCreateResponse>>
    get() = repo.taskcreatelivedata

    val resedittask:LiveData<Response<EditTaskResponse>>
    get() = repo.taskeditlivedata
}