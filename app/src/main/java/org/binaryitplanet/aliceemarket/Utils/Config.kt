package org.binaryitplanet.aliceemarket.Utils

class Config {
    companion object {
        // Toolbar title
        val PRODUCTS = "Products"
        val LOGIN_TOOLBAR = "Sign In"
        val EDIT_PRODUCT_TOOLBAR = "Edit product details"
        val ADD_PRODUCT_TOOLBAR = "Add product details"



        val REQUIRED_FIELD = "Required field"
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

        val CATEGORIES = arrayListOf(
                "Fruit",
                "Livestock",
                "Poultry",
                "Vegetable"
        )

        val UNITS = arrayListOf(
                "Piece",
                "Gram",
                "KG",
                "Litter",
                "Gallon",
                "Centimeter",
                "Foot",
                "Inch",
                "Kilometer",
                "Meter",
                "Tonne"
        )
        val SEARCH_HINT = "Product name"

        // Intent data
        val PRODUCT = "Product"
        val IS_SELLER = "isSeller"
        val IS_EDIT = "isEdit"
    }
}