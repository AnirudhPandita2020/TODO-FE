package com.anirudh.todo_fe.dataclass

data class TaskCreateResponse(
    val content: String,
    val created_at: String,
    val id: Int,
    val is_completed: Boolean,
    val title: String,
    val user: UserX,
    val userid: Int
)