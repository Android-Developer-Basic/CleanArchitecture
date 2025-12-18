package ru.otus.location.huawei.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.otus.domain.usecase.LocationProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class HuaweiModule {
    @Binds
    abstract fun locationProvider(impl: HuaweiLocationProvider): LocationProvider
}