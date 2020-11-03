package org.binaryitplanet.aliceemarket.Features.Model

import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.ProductUtils

interface ProductModel {
    fun addProduct(
            userId: String,
            product: ProductUtils,
            callback: OnRequestCompleteListener<Boolean>
    )


    fun getProductListByUserId(
            userId: String,
            callback: OnRequestCompleteListener<ArrayList<ProductUtils>>
    )

    fun getProductList(
            callback: OnRequestCompleteListener<ArrayList<ProductUtils>>
    )


}