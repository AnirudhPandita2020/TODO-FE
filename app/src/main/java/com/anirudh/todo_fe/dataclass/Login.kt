package com.anirudh.todo_fe.dataclass

data class Login(
    val access_token: String,
    val email: String,
    val token_type: String,
    val userid: Int,
    val username: String
)