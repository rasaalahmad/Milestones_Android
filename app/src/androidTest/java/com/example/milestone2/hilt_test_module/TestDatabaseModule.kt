package com.example.milestone2.hilt_test_module

import android.content.Context
import androidx.room.Room
import com.example.milestone2.hilt.AppModule
import com.example.milestone2.room_database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
object TestDatabaseModule {
    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun getContactDatabase(@ApplicationContext ctx: Context):
            AppDatabase = Room.inMemoryDatabaseBuilder(ctx, AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()


    @Singleton
    @Provides
    fun provideContactDao(db: AppDatabase) = db.contactDao() // The reason we can implement a Dao for the database
}