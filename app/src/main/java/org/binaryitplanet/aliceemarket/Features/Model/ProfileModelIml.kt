package org.binaryitplanet.aliceemarket.Features.Model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProfileUtils
import javax.inject.Inject

class ProfileModelIml @Inject constructor(): ProfileModel {

    private val TAG = "ProfileModel"

    override fun setProfile(userId: String, profile: ProfileUtils, callback: OnRequestCompleteListener<Boolean>) {
        FirebaseFirestore
                .getInstance()
                .collection(Config.PROFILE_PATH)
                .document(userId)
                .set(profile)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        callback.onSuccess(true)
                    } else {
                        callback.onFailed("Profile creation failed")
                        Log.d(TAG, "CreateProfileFailed: ${it.result.toString()}")
                    }
                }
    }

    override fun getProfile(userId: String, callback: OnRequestCompleteListener<ProfileUtils?>) {
        FirebaseFirestore
                .getInstance()
                .collection(Config.PROFILE_PATH)
                .document(userId)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.d(TAG, "GetProfileError: ${error.message}")
                        callback.onFailed(error.message!!)
                        return@addSnapshotListener
                    }
                    callback.onSuccess(value?.toObject(ProfileUtils::class.java) as ProfileUtils)
                }
    }
}