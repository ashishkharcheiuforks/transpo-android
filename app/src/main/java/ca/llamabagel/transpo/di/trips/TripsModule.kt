/*
 * Copyright (c) 2019 Derek Ellis. Subject to the MIT license.
 */

package ca.llamabagel.transpo.di.trips

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import ca.llamabagel.transpo.BuildConfig
import ca.llamabagel.transpo.data.TripsRepository
import ca.llamabagel.transpo.data.api.TripsService
import ca.llamabagel.transpo.data.db.TransitDatabase
import ca.llamabagel.transpo.di.scope.FeatureScope
import ca.llamabagel.transpo.ui.trips.TripsActivity
import ca.llamabagel.transpo.ui.trips.TripsViewModel
import ca.llamabagel.transpo.ui.trips.TripsViewModelFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
abstract class TripsModule {

    @Binds
    abstract fun tripsActivityAsFragmentActivity(activity: TripsActivity): FragmentActivity

    @Module
    companion object {
        @Provides
        @FeatureScope
        @JvmStatic
        fun provideTripsService(adapter: CoroutineCallAdapterFactory, converter: Converter.Factory, okHttpClient: OkHttpClient): TripsService = Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(okHttpClient)
            .addCallAdapterFactory(adapter)
            .addConverterFactory(converter)
            .build().create(TripsService::class.java)

        @Provides
        @FeatureScope
        @JvmStatic
        fun provideTripsRepository(transitDatabase: TransitDatabase, tripsService: TripsService) =
            TripsRepository(transitDatabase, tripsService)

        @Provides
        @JvmStatic
        fun tripsViewModel(
            factory: TripsViewModelFactory,
            fragmentActivity: FragmentActivity
        ): TripsViewModel {
            return ViewModelProviders.of(fragmentActivity, factory)[TripsViewModel::class.java]
        }

    }
}