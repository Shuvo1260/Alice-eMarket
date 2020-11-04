package org.binaryitplanet.aliceemarket.Features.Model

import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.NewsUtils

interface NewsModel {
    fun getNewsList(callback: OnRequestCompleteListener<ArrayList<NewsUtils>>)
}