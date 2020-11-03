package org.binaryitplanet.aliceemarket.Features.Common

interface OnRequestCompleteListener<T> {
    fun onSuccess(data: T)
    fun onFailed(message: String)
}