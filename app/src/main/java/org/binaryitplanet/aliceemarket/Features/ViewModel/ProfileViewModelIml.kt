package org.binaryitplanet.aliceemarket.Features.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Features.Model.ProfileModel
import org.binaryitplanet.aliceemarket.Utils.ProfileUtils
import javax.inject.Inject

class ProfileViewModelIml @Inject constructor(
        private val model: ProfileModel
): ViewModel(), ProfileViewModel {

    private val TAG = "ProfileViewModel"

    private var userId: String

    val setProfileSuccess = MutableLiveData<Boolean>()
    val setProfileFailed = MutableLiveData<String>()
    val getProfileFailed = MutableLiveData<String>()
    val getProfileSuccess = MutableLiveData<ProfileUtils?>()

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser!!
        userId = currentUser.email!!.split("@")[0]
    }


    override fun setProfile(profile: ProfileUtils) {
        model.setProfile(
                userId,
                profile,
                object : OnRequestCompleteListener<Boolean>{
                    override fun onSuccess(data: Boolean) {
                        setProfileSuccess.postValue(data)
                    }

                    override fun onFailed(message: String) {
                        Log.d(TAG, "ProfileFailed: $message")
                        setProfileFailed.postValue(message)
                    }

                }
        )
    }

    override fun getProfile() {
        model.getProfile(
                userId,
                object : OnRequestCompleteListener<ProfileUtils?>{
                    override fun onSuccess(data: ProfileUtils?) {
                        getProfileSuccess.postValue(data)
                    }

                    override fun onFailed(message: String) {
                        Log.d(TAG, "ProfileFetchFailed: $message")
                        getProfileFailed.postValue(message)
                    }

                }
        )
    }
}