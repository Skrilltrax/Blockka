package dev.skrilltrax.blockka.injection

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.skrilltrax.blockka.model.BlockkaDatabase
import dev.skrilltrax.blockka.model.BlockkaDatabase.Companion.Schema
import dev.skrilltrax.sqldelight.ContactQueries
import dev.skrilltrax.sqldelight.RecentContactQueries
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SqlDelightModule {

  @Provides
  @Singleton
  fun provideAndroidDriver(@ApplicationContext applicationContext: Context): SqlDriver {
    return AndroidSqliteDriver(Schema, applicationContext, "blockedContacts.db")
  }

  @Provides
  @Singleton
  fun provideContactsDatabase(driver: SqlDriver): BlockkaDatabase {
    return BlockkaDatabase(driver)
  }

  @Provides
  @Singleton
  fun provideContactQueries(database: BlockkaDatabase): ContactQueries {
    return database.contactQueries
  }

  @Provides
  @Singleton
  fun provideRecentContactQueries(database: BlockkaDatabase): RecentContactQueries {
    return database.recentContactQueries
  }
}
