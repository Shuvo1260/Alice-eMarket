package org.binaryitplanet.aliceemarket.Utils

import java.io.Serializable

data class ProductUtils(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val category: String
) : Serializable {
}