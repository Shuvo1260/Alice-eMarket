package org.binaryitplanet.aliceemarket.Utils

class Config {
    companion object {
        // Toolbar title
        val PRODUCTS = "Products"
        val CACHE_SIZE = 1000
        val CURRENCY_SIGN = "ZAR"

        val PRODUCT_CATEGORIES = arrayListOf(
            "All",
            "Fruit",
            "Livestock",
            "Poultry",
            "Vegetable"
        )

        val SEARCH_HINT = "Product name"

        // Intent data
        val PRODUCT = "Product"
        val IS_SELLER = "isSeller"
    }
}