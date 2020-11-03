package org.binaryitplanet.aliceemarket.Features.Components

import android.content.Context
import dagger.Component
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProductViewModelIml
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModel
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModelIml

@Component(modules = [AppModule::class])
interface AppComponents {
    fun getProfileViewModel(): ProfileViewModelIml

    fun getProductViewModel(): ProductViewModelIml
}