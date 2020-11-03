package org.binaryitplanet.aliceemarket.Features.Model

import android.net.Uri
import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.ProductUtils

interface ProductModel {
    fun uploadProduct(
            userId: String,
            imageName: String,
            imageUri: Uri,
            product: ProductUtils,
            callback: OnRequestCompleteListener<Boolean>
    )

    fun deleteProduct(
            product: ProductUtils,
            callback: OnRequestCompleteListener<Boolean>
    )


    fun getProductListByUserId(
            userEmail: String,
            callback: OnRequestCompleteListener<ArrayList<ProductUtils>>
    )

    fun getProductList(
            callback: OnRequestCompleteListener<ArrayList<ProductUtils>>
    )


}