package com.keytotech.task.koin

import com.keytotech.task.api.CommentApiImpl
import com.keytotech.task.api.CommentLoadApi
import org.koin.dsl.bind
import org.koin.dsl.module

object Modules {

    val module = module {
        single { CommentApiImpl() } bind  CommentLoadApi::class
    }

}