package com.keytotech.task.model

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.keytotech.task.api.CommentLoadAdi
import com.keytotech.task.data.CommentsData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.getKoin
import java.lang.Exception


class CommentsDataViewModel(app:Application) : AndroidViewModel(app){

    companion object {
        private const val TAG = "CommentsDataViewModel"
        private const val LOAD_AMMOUNT = 10
    }

    private val lowerBoundLiveData = MutableLiveData<Int>()
    private val upperBoundLiveData = MutableLiveData<Int>()
    private val commentsLiveData = MutableLiveData<CommentsData>()
    private val commentLoadAdi : CommentLoadAdi  = app.getKoin().get()
    private val disposables = CompositeDisposable()

    fun getCommentsData() = commentsLiveData

    fun setBounds(lower : String, upper:String){
        lowerBoundLiveData.value = lower.toInt()
        upperBoundLiveData.value = upper.toInt()
    }

    private var prevPage = -1

    @SuppressLint("CheckResult")
    fun loadComments(page:Int){

        Log.i(TAG, "loadComments, page: $page")
        if(page == prevPage){
            return
        }
        prevPage = page

        var lowerBound = lowerBoundLiveData.value ?: throw Exception("Something terribly wrong")
        var upperBound: Int

        if(LOAD_AMMOUNT > upperBoundLiveData.value!!){
            loadFromApi(lowerBoundLiveData.value!!,upperBoundLiveData.value!!, upperBoundLiveData.value!!- lowerBoundLiveData.value!!)
            prevPage+=1
        }else {
            lowerBound += (page) * LOAD_AMMOUNT
            upperBound = lowerBound + LOAD_AMMOUNT
            if (lowerBound >= upperBoundLiveData.value!!) {
                commentsLiveData.value = CommentsData.empty()
                return
            }
            if (upperBound > upperBoundLiveData.value!!) {

                loadFromApi(lowerBound, upperBoundLiveData.value!!, upperBound - lowerBound)
            }else {
                loadFromApi(lowerBound, upperBound, LOAD_AMMOUNT)
            }
        }
    }


    private fun loadFromApi(startId: Int, endId:Int, size: Int){
        Log.i(TAG, "loadFromApi  current slice: from: $startId to $endId")

        disposables.add(Observable
            .fromIterable(startId..endId)
            .flatMap {
                Log.i(TAG, "loadComments, id: $it")

                val comments =  commentLoadAdi.loadComment(it).toObservable()

                Observable.fromArray(CommentsData.fromList(comments.blockingSingle()))
            }
            .toList(size)
            .map(CommentsData.Companion::merge)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe({
                commentsLiveData.value = it
            }, {
                Log.i(TAG, "Error: $it")
            }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun clear(){
        commentsLiveData.value = CommentsData.empty()
    }
}