package org.binaryitplanet.aliceemarket.Utils

import java.io.Serializable

data class ProductUtils(
    val id: String,
    val name: String,
    var imageUrl: String,
    val price: Double,
    val category: String,
    val quantity: String,
    val unit: String,
    val sellerName: String,
    val sellerPhone: String,
    val sellerEmail: String,
    val sellerLocation: String,
    val sellerMessage: String
) : Serializable {
}