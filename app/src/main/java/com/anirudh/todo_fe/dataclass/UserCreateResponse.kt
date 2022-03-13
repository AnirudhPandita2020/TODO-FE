package com.anirudh.todo_fe.dataclass

data class UserCreateResponse(
    var created_at: String,
    var email: String,
    var id: Int
)