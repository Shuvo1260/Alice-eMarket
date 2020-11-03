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

    private var userId: String
    private var userEmail: String

    val onUploadSuccessLiveData = MutableLiveData<Boolean>()
    val onUploadFailedLiveData = MutableLiveData<String>()
    val onDeleteSuccessLiveData = MutableLiveData<Boolean>()
    val onDeleteFailedLiveData = MutableLiveData<String>()

    val onGetListSuccessLiveData = MutableLiveData<MutableList<ProductUtils>>()
    val onGetListByUserIdSuccessLiveData = MutableLiveData<MutableList<ProductUtils>>()

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser!!
        userEmail = currentUser.email!!
        userId = userEmail!!.split("@")[0]
    }

    override fun uploadProduct(imageName: String, imageUri: Uri, product: ProductUtils) {
        Log.d(TAG, "Uploading product")
        model
                .uploadProduct(
                        userId,
                        imageName,
                        imageUri,
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

    override fun getProductListByUserId() {
        model
                .getProductListByUserId(
                        userEmail,
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