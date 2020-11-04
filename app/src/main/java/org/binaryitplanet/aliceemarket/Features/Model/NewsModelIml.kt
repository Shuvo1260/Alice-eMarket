package org.binaryitplanet.aliceemarket.Features.Model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.NewsUtils
import java.lang.Exception
import javax.inject.Inject

class NewsModelIml @Inject constructor(): NewsModel {

    private val TAG = "NewsModel"

    override fun getNewsList(callback: OnRequestCompleteListener<ArrayList<NewsUtils>>) {
        var newsList = arrayListOf<NewsUtils>()
        FirebaseFirestore
                .getInstance()
                .collection(Config.NEWS_PATH)
                .get()
                .addOnSuccessListener {
                    try {
                        for (doc in it.documentChanges) {
                            var document = doc.document
                            var news = document.toObject(NewsUtils ::class.java)
                            newsList.add(news)
                        }
                        callback.onSuccess(newsList)
                    } catch (e: Exception) {
                        Log.d(TAG, "NewsFetchingException: ${e.message}")
                        callback.onFailed(e.message!!)
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "NewsFetchingFailed: ${it.message}")
                    callback.onFailed(it.message!!)
                }
    }
}