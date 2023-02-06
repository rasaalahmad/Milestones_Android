package com.example.milestone2.hilt

import android.content.Context
import androidx.room.Room
import com.example.milestone2.room_database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    @Synchronized
    fun getContactDatabase(@ApplicationContext ctx: Context): AppDatabase = Room.databaseBuilder(ctx.applicationContext, AppDatabase::class.java,
            "Contacts_App")
            .fallbackToDestructiveMigration()
            .build()


    @Singleton
    @Provides
    fun provideContactDao(db: AppDatabase) = db.contactDao() // The reason we can implement a Dao for the database
}