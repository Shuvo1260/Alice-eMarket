package org.binaryitplanet.aliceemarket.Features.ViewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Features.Model.ProductModel
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import javax.inject.Inject

class ProductViewModelIml @Inject constructor(
        private val model: ProductModel
): ViewModel(), ProductViewModel {

    private val TAG = "ProductViewModel"

    private var userId: String? = null
    private var userEmail: String? = null

    val onUploadSuccessLiveData = MutableLiveData<Boolean>()
    val onUploadFailedLiveData = MutableLiveData<String>()
    val onDeleteSuccessLiveData = MutableLiveData<Boolean>()
    val onDeleteFailedLiveData = MutableLiveData<String>()

    val onGetListSuccessLiveData = MutableLiveData<MutableList<ProductUtils>>()
    val onGetListByUserIdSuccessLiveData = MutableLiveData<MutableList<ProductUtils>>()

    val onGetProductSuccessLiveData = MutableLiveData<ProductUtils>()
    val onGetProductFailedLiveData = MutableLiveData<String>()

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            userEmail = currentUser?.email!!
            userId = userEmail!!.split("@")[0]
        }
    }

    override fun uploadProduct(imageName: String?, imageUri: Uri?, product: ProductUtils) {
        Log.d(TAG, "Uploading product")

        if (imageName.isNullOrEmpty()) {
            uploadProductData(product)
        } else {
            model.uploadImage(
                    imageName!!,
                    imageUri!!,
                    object : OnRequestCompleteListener<String> {
                        override fun onSuccess(data: String) {
                            product.imageUrl = data
                            uploadProductData(product)
                        }

                        override fun onFailed(message: String) {
                            Log.d(TAG, "ImageUploadingFailed: $message")
                        }

                    }
            )
        }
    }

    fun uploadProductData(product: ProductUtils) {
        model
                .uploadProduct(
                        product,
                        object : OnRequestCompleteListener<Boolean> {
                            override fun onSuccess(data: Boolean) {
                                onUploadSuccessLiveData.postValue(data)
                            }

                            override fun onFailed(message: String) {
                                onUploadFailedLiveData.postValue(message)
                            }

                        }
                )
    }

    override fun deleteProduct(product: ProductUtils) {
        model
                .deleteProduct(
                        product,
                        object : OnRequestCompleteListener<Boolean> {
                            override fun onSuccess(data: Boolean) {
                                onDeleteSuccessLiveData.postValue(data)
                            }

                            override fun onFailed(message: String) {
                                onDeleteFailedLiveData.postValue(message)
                            }

                        }
                )
    }

    override fun getProduct(id: String) {
        model
                .getProduct(
                        id,
                        object : OnRequestCompleteListener<ProductUtils>{
                            override fun onSuccess(data: ProductUtils) {
                                onGetProductSuccessLiveData.postValue(data)
                            }

                            override fun onFailed(message: String) {
                                Log.d(TAG, "ProductFetchingFailed: $message")
                                onGetProductFailedLiveData.postValue(message)
                            }

                        }
                )
    }

    override fun getProductListByUserId() {
        Log.d(TAG, "ProductListByUserEmail...")
        model
                .getProductListByUserId(
                        userEmail!!,
                        object : OnRequestCompleteListener<ArrayList<ProductUtils>> {
                            override fun onSuccess(data: ArrayList<ProductUtils>) {
                                onGetListByUserIdSuccessLiveData.postValue(data)
                            }

                            override fun onFailed(message: String) {
                                Log.d(TAG, "ProductListByUserIdFailed: $message")
                            }

                        }
                )
    }

    override fun getProductList(category: String) {
        model
                .getProductList(
                        category,
                        object : OnRequestCompleteListener<ArrayList<ProductUtils>>{
                            override fun onSuccess(data: ArrayList<ProductUtils>) {
                                onGetListSuccessLiveData.postValue(data)
                            }

                            override fun onFailed(message: String) {
                                Log.d(TAG, "ProductListFailed: $message")
                            }

                        }
                )
    }

}