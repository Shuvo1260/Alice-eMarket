package org.binaryitplanet.aliceemarket.Features.ViewModel

import android.net.Uri
import org.binaryitplanet.aliceemarket.Utils.ProductUtils

interface ProductViewModel {
    fun uploadProduct(
            imageName: String,
            imageUri: Uri,
            product: ProductUtils
    )

    fun deleteProduct(
            product: ProductUtils
    )

    fun getProductListByUserId()

    fun getProductList(category: String)
}