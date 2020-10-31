package org.binaryitplanet.aliceemarket.Utils

class Config {
    companion object {
        // Toolbar title
        val PRODUCTS = "Products"
        val LOGIN_TOOLBAR = "Sign In"



        val CACHE_SIZE = 1000
        val CURRENCY_SIGN = "ZAR"
        val CHOOSE_EMAIL_MESSAGE = "Send email via..."
        val OPEN_EMAIL_FAILED_MESSAGE = "Failed to open"
        val SIGN_IN_PROGRESS_TITLE = "Signing in..."
        val SIGN_IN_PROGRESS_MESSAGE = "Please wait a moment"
        val SIGN_IN_SUCCESSFUL_MESSAGE = "Successfully signed in"
        val SIGN_IN_FAILED_MESSAGE = "Sign in failed"
        val RC_SIGN_IN = 100

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