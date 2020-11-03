package org.binaryitplanet.aliceemarket.Features.Model

import org.binaryitplanet.aliceemarket.Features.Common.OnRequestCompleteListener
import org.binaryitplanet.aliceemarket.Utils.ProfileUtils

interface ProfileModel {
    fun setProfile(
            userId: String,
            profile: ProfileUtils,
            callback: OnRequestCompleteListener<Boolean>
    )

    fun getProfile(
            userId: String,
            callback: OnRequestCompleteListener<ProfileUtils?>
    )
}