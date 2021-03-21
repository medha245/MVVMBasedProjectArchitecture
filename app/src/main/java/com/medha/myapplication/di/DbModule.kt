package com.medha.myapplication.di

import android.content.Context
import androidx.room.Room
import com.medha.myapplication.db.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {
    // singleton meaning you want all components of this app to use same instance of whatever it returns
    // Provides will make sure that wherever you define @Inject for this database it will look for
    // this return type in its corresponding component i.e application component

    // we can also define activity based component like a viewmodel / repository
    // which can be shared between two fragments
    // will have same instance of repository available in activity
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDb {
        return Room.databaseBuilder(
            appContext.applicationContext,
            RoomDb::class.java,
            "room_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}