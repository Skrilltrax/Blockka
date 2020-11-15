/*
package dev.skrilltrax.blockka.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.skrilltrax.blockka.data.repo.BlockkaRepository
import dev.skrilltrax.sqldelight.ContactQueries
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun providesBlockkaRepository(contactQueries: ContactQueries): BlockkaRepository {
        return BlockkaRepository(contactQueries)
    }
}
*/
