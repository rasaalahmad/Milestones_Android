package com.example.milestone2.hilt

import android.app.Application
import com.example.milestone2.room_database.ContactDatabaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContactDatabaseClient(application: Application):ContactDatabaseClient{
        return ContactDatabaseClient(application)
    }
}