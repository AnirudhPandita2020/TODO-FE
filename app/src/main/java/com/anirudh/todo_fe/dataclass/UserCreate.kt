package com.anirudh.todo_fe.dataclass

data class UserCreate(
    val email: String,
    val password: String,
    val username: String
)