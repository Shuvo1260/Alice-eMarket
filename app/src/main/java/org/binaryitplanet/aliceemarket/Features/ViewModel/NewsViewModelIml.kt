package org.binaryitplanet.aliceemarket.Features.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Features.Model.NewsModel
import org.binaryitplanet.aliceemarket.Utils.NewsUtils
import javax.inject.Inject

class NewsViewModelIml @Inject constructor(
        private val model: NewsModel
): ViewModel(), NewsViewModel {

    private val TAG = "NewsViewModel"

    val newsListLiveData = MutableLiveData<MutableList<NewsUtils>>()

    override fun getNewsList() {
        model.getNewsList(object : OnRequestCompleteListener<ArrayList<NewsUtils>>{
            override fun onSuccess(data: ArrayList<NewsUtils>) {
                newsListLiveData.postValue(data)
            }

            override fun onFailed(message: String) {
                Log.d(TAG, "NewsFetchingError: $message")
            }

        })
    }
}