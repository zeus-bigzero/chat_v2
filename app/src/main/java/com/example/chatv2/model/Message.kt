package com.example.chatv2.model

data class Message(
    val id: Long = System.currentTimeMillis(), // custom ????

    val msg: String, // msg is empty -> tin nhắn hình ảnh
    val path: String, // msg is not empty, thì path vô nghĩa.
    val time: String, // System.currentTime
    val isSelf: Boolean // true is me, false is you
)