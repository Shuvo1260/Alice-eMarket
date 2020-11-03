package org.binaryitplanet.aliceemarket.Features.Components

import dagger.Module
import dagger.Provides
import org.binaryitplanet.aliceemarket.Features.Model.ProfileModel
import org.binaryitplanet.aliceemarket.Features.Model.ProfileModelIml
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModelIml

@Module
class AppModule {

    @Provides
    fun provideProfileModel(): ProfileModelIml{
        return ProfileModelIml()
    }

    @Provides
    fun provideProfileViewModel(model: ProfileModelIml): ProfileViewModelIml {
        return ProfileViewModelIml(model)
    }
}