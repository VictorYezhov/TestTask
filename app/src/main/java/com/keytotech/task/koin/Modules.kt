package com.keytotech.task.koin

import com.keytotech.task.api.CommentAdiImpl
import com.keytotech.task.api.CommentLoadAdi
import com.keytotech.task.model.CommentsDataViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object Modules {

    val module = module {
        single { CommentAdiImpl() } bind  CommentLoadAdi::class
    }

}