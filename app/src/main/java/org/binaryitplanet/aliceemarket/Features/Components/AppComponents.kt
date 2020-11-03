package org.binaryitplanet.aliceemarket.Features.Components

import dagger.Component
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModel
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModelIml

@Component(modules = [AppModule::class])
interface AppComponents {
    fun getProfileViewModel(): ProfileViewModelIml
}