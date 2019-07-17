package com.keytotech.task.data

import com.google.gson.annotations.SerializedName

data class Comment(
    @field:SerializedName("id")
    val id : Int,
    @field:SerializedName("postId")
    val postId:String,
    @field:SerializedName("name")
    val name : String,
    @field:SerializedName("email")
    val email : String,
    @field:SerializedName("body")
    val body: String
)