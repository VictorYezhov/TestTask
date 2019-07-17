package com.keytotech.task.data

import io.reactivex.Observable
import java.util.*

class CommentsData private constructor(initialComments: List<Comment>){

    val comments = ArrayList<Comment>()

    init {
        comments.addAll(initialComments)
    }

    override fun toString(): String {
        return "$comments"
    }

    companion object {

        fun fromList(comments: List<Comment>): CommentsData {
            return CommentsData(comments)
        }

        fun merge(pricesData: List<CommentsData>): CommentsData {
            return Observable
                .fromIterable(pricesData)
                .flatMap {
                        i -> Observable.fromIterable(i.comments)
                }
                .toList()
                .map {
                    it.sortBy {
                        it.id
                    }
                    it
                }
                .map<CommentsData>{ fromList(it) }
                .blockingGet()
        }

        fun empty(): CommentsData {
            return fromList(Collections.emptyList())
        }
    }
}