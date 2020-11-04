package org.binaryitplanet.aliceemarket.Features.Components

import dagger.Module
import dagger.Provides
import org.binaryitplanet.aliceemarket.Features.Model.NewsModelIml
import org.binaryitplanet.aliceemarket.Features.Model.ProductModel
import org.binaryitplanet.aliceemarket.Features.Model.ProductModelIml
import org.binaryitplanet.aliceemarket.Features.Model.ProfileModelIml
import org.binaryitplanet.aliceemarket.Features.ViewModel.NewsViewModelIml
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProductViewModelIml
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

    @Provides
    fun provideProductModel(): ProductModelIml {
        return ProductModelIml()
    }

    @Provides
    fun provideProductViewModel(model: ProductModelIml): ProductViewModelIml {
        return ProductViewModelIml(model)
    }

    @Provides
    fun provideNewsModel(): NewsModelIml {
        return NewsModelIml()
    }

    @Provides
    fun provideNewsViewModel(model: NewsModelIml) : NewsViewModelIml {
        return NewsViewModelIml(model)
    }
}