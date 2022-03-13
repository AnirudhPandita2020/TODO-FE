package com.anirudh.todo_fe.MainViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anirudh.todo_fe.Repo.Repo

class ViewModelFactory(private val repo:Repo):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModel(repo) as T
    }
}