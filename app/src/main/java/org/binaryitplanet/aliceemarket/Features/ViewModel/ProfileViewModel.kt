package org.binaryitplanet.aliceemarket.Features.ViewModel

import org.binaryitplanet.aliceemarket.Utils.ProfileUtils

interface ProfileViewModel {
    fun setProfile(profile: ProfileUtils)

    fun getProfile()
}