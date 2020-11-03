package org.binaryitplanet.aliceemarket.Features.Model

import android.net.Uri
import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.ProductUtils

interface ProductModel {
    fun uploadImage(
            imageName: String,
            imageUri: Uri,
            callback: OnRequestCompleteListener<String>
    )
    fun uploadProduct(
            product: ProductUtils,
            callback: OnRequestCompleteListener<Boolean>
    )

    fun deleteProduct(
            product: ProductUtils,
            callback: OnRequestCompleteListener<Boolean>
    )

    fun getProduct(
            id: String,
            callback: OnRequestCompleteListener<ProductUtils>
    )


    fun getProductListByUserId(
            userEmail: String,
            callback: OnRequestCompleteListener<ArrayList<ProductUtils>>
    )

    fun getProductList(
            category: String,
            callback: OnRequestCompleteListener<ArrayList<ProductUtils>>
    )


}