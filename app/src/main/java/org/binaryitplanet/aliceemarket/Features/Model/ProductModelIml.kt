package org.binaryitplanet.aliceemarket.Features.Model

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import java.lang.Exception
import javax.inject.Inject

class ProductModelIml @Inject constructor(): ProductModel {

    private val TAG = "ProductModel"

    override fun uploadProduct(
            userId: String,
            imageName: String,
            imageUri: Uri,
            product: ProductUtils,
            callback: OnRequestCompleteListener<Boolean>
    ) {
        FirebaseStorage
                .getInstance()
                .reference
                .child(imageName)
                .putFile(imageUri)
                .addOnSuccessListener {
                    try {
                        val taskUri = it.storage.downloadUrl

                        while (!taskUri.isSuccessful);

                        val downloadUri = taskUri.result

                        Log.d(TAG, "DownloadUrl: $downloadUri")

                        product.imageUrl = downloadUri.toString()
                        FirebaseFirestore
                                .getInstance()
                                .collection(Config.PRODUCT_PATH)
                                .document(product.id)
                                .set(product)
                                .addOnCompleteListener {task ->
                                    if (task.isSuccessful) {
                                        callback.onSuccess(true)
                                    } else {
                                        Log.d(TAG, "UploadProductFailed: ${task.result.toString()}")
                                        callback.onFailed("Product uploading failed")
                                    }
                                }
                    } catch (e: Exception) {
                        Log.d(TAG, "ImageException: ${e.message} ")
                        callback.onFailed("Image uploading problem")
                    }
                } .addOnFailureListener {
                    Log.d(TAG, "ImageUploadingFailed: ${it.message} ")
                    callback.onFailed("Image uploading failed")
                }
    }

    override fun deleteProduct(product: ProductUtils, callback: OnRequestCompleteListener<Boolean>) {
        val storage = FirebaseStorage
                .getInstance().getReferenceFromUrl(product.imageUrl)
        storage
                .delete()
                .addOnSuccessListener {
                    FirebaseFirestore
                            .getInstance()
                            .collection(Config.PRODUCT_PATH)
                            .document(product.id)
                            .delete()
                            .addOnSuccessListener {
                                Log.d(TAG, "Product deleted successfully.")
                                callback.onSuccess(true)
                            }
                            .addOnFailureListener {
                                Log.d(TAG, "ProductDeletionFailed: ${it.message} ")
                                callback.onFailed("Product deletion failed")
                            }
                }
                .addOnFailureListener {
                    Log.d(TAG, "ImageDeletionFailed: ${it.message} ")
                    callback.onFailed("Image deletion failed")
                }
    }

    override fun getProductListByUserId(userEmail: String, callback: OnRequestCompleteListener<ArrayList<ProductUtils>>) {
        var productList = arrayListOf<ProductUtils>()

        FirebaseFirestore
                .getInstance()
                .collection(Config.PRODUCT_PATH)
                .whereEqualTo(Config.SELLER_EMAIL, userEmail)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.d(TAG, "ProductListByUserFailed: ${error.message}")
                        callback.onFailed("Product uploading failed")
                        return@addSnapshotListener
                    }

                    try {
                        for (doc in value!!.documentChanges) {
                            val product = ProductUtils(
                                    doc.document.getString(Config.ID)!!,
                                    doc.document.getString(Config.NAME)!!,
                                    doc.document.getString(Config.IMAGE_URL)!!,
                                    doc.document.getDouble(Config.PRICE)!!,
                                    doc.document.getString(Config.CATEGORY)!!,
                                    doc.document.getString(Config.QUANTITY)!!,
                                    doc.document.getString(Config.UNIT)!!,
                                    doc.document.getString(Config.SELLER_NAME)!!,
                                    doc.document.getString(Config.SELLER_PHONE)!!,
                                    doc.document.getString(Config.SELLER_EMAIL)!!,
                                    doc.document.getString(Config.SELLER_LOCATION)!!,
                                    doc.document.getString(Config.SELLER_MESSAGE)!!
                            )
                            when (doc.type) {
                                DocumentChange.Type.ADDED -> {
                                    productList.add(product)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    for (index in 0 until productList.size) {
                                        if (productList[index].id == product.id) {
                                            productList[index] = product
                                        }
                                    }
                                }
                                DocumentChange.Type.REMOVED -> {
                                    productList.remove(product)
                                }
                            }
                        }
                        callback.onSuccess(productList)
                    } catch (e: Exception) {
                        Log.d(TAG, "ProductListByUserException: ${e.message}")
                        callback.onFailed("Product fetching failed")
                    }

                }
    }

    override fun getProductList(category: String, callback: OnRequestCompleteListener<ArrayList<ProductUtils>>) {
        var productList = arrayListOf<ProductUtils>()

        FirebaseFirestore
                .getInstance()
                .collection(Config.PRODUCT_PATH)
                .whereEqualTo(Config.CATEGORY, category)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.d(TAG, "ProductListFailed: ${error.message}")
                        callback.onFailed("Product uploading failed")
                        return@addSnapshotListener
                    }

                    try {
                        for (doc in value!!.documentChanges) {
                            val product = ProductUtils(
                                    doc.document.getString(Config.ID)!!,
                                    doc.document.getString(Config.NAME)!!,
                                    doc.document.getString(Config.IMAGE_URL)!!,
                                    doc.document.getDouble(Config.PRICE)!!,
                                    doc.document.getString(Config.CATEGORY)!!,
                                    doc.document.getString(Config.QUANTITY)!!,
                                    doc.document.getString(Config.UNIT)!!,
                                    doc.document.getString(Config.SELLER_NAME)!!,
                                    doc.document.getString(Config.SELLER_PHONE)!!,
                                    doc.document.getString(Config.SELLER_EMAIL)!!,
                                    doc.document.getString(Config.SELLER_LOCATION)!!,
                                    doc.document.getString(Config.SELLER_MESSAGE)!!
                            )
                            when (doc.type) {
                                DocumentChange.Type.ADDED -> {
                                    productList.add(product)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    for (index in 0 until productList.size) {
                                        if (productList[index].id == product.id) {
                                            productList[index] = product
                                        }
                                    }
                                }
                                DocumentChange.Type.REMOVED -> {
                                    productList.remove(product)
                                }
                            }
                        }
                        callback.onSuccess(productList)
                    } catch (e: Exception) {
                        Log.d(TAG, "ProductListException: ${e.message}")
                        callback.onFailed("Product fetching failed")
                    }

                }
    }
}