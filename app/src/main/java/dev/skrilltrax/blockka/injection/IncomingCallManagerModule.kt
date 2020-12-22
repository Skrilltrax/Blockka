package dev.skrilltrax.blockka.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.skrilltrax.blockka.data.manager.IncomingCallManager

@InstallIn(ApplicationComponent::class)
@Module
object IncomingCallManagerModule {

  @Provides
  @Reusable
  fun provideIncomingCallManager(@ApplicationContext context: Context): IncomingCallManager {
    return IncomingCallManager.getInstance(context)
  }
}
