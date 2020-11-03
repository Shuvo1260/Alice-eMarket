package org.binaryitplanet.aliceemarket.Utils

class Config {
    companion object {
        // Toolbar title
        val PRODUCTS = "Products"
        val LOGIN_TOOLBAR = "Sign In"
        val EDIT_PRODUCT_TOOLBAR = "Edit product details"
        val ADD_PRODUCT_TOOLBAR = "Add product details"
        val EDIT_PROFILE_TOOLBAR = "Edit profile"
        val SET_PROFILE = "Profile"



        val REQUIRED_FIELD = "Required field"
        val FAILED_MESSAGE = "Operation failed"
        val IMAGE_EMPTY_MESSAGE = "Please select an image"
        val COMPLETE_PROFILE_MESSAGE = "Please complete your profile"
        val CACHE_SIZE = 1000
        val CURRENCY_SIGN = "ZAR"
        val CHOOSE_EMAIL_MESSAGE = "Send email via..."
        val OPEN_EMAIL_FAILED_MESSAGE = "Failed to open"
        val SIGN_IN_PROGRESS_TITLE = "Signing in..."
        val SIGN_IN_PROGRESS_MESSAGE = "Please wait a moment"
        val SIGN_IN_SUCCESSFUL_MESSAGE = "Successfully signed in"
        val SIGN_IN_FAILED_MESSAGE = "Sign in failed"
        val PROFILE_UPDATED_SUCCESSFULLY = "Profile updated successfully"
        val PROFILE_UPDATE_FAILED = "Profile update failed"
        val RC_SIGN_IN = 100
        val PICK_IMAGE_REQUEST_CODE = 101

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

        // Product Items
        val ID = "id"
        val NAME = "name"
        val IMAGE_URL = "imageUrl"
        val PRICE = "prince"
        val CATEGORY = "category"
        val QUANTITY = "quantity"
        val UNIT = "unit"
        val SELLER_NAME = "sellerName"
        val SELLER_PHONE = "sellerPhone"
        val SELLER_EMAIL = "sellerEmail"
        val SELLER_LOCATION = "sellerLocation"
        val SELLER_MESSAGE = "sellerMessage"

        // Intent data
        val PRODUCT = "Product"
        val PROFILE = "Profile"
        val IS_SELLER = "isSeller"
        val IS_EDIT = "isEdit"
        val PICK_IMAGE = "Pick image"
        val PRODUCT_TYPE = "productType"


        // Database paths
        val PROFILE_PATH = "Sellers"
        val PRODUCT_PATH = "Products"
    }
}