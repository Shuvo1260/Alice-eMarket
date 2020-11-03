package org.binaryitplanet.aliceemarket.Features.Model

import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import javax.inject.Inject

class ProductModelIml @Inject constructor(): ProductModel {
    override fun addProduct(userId: String, product: ProductUtils, callback: OnRequestCompleteListener<Boolean>) {
        //
    }

    override fun getProductListByUserId(userId: String, callback: OnRequestCompleteListener<ArrayList<ProductUtils>>) {
        //
    }

    override fun getProductList(callback: OnRequestCompleteListener<ArrayList<ProductUtils>>) {
        //
    }
}