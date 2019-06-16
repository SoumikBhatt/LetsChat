package com.soumik.letschat.model

import java.util.*

data class Messages(
    val messageBody:String="",
    val messageUser:String="",
    val messageUserID:String="",
    val messageTime:Long=Date().time
)