package ytemplate.android.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ytemplate.android.database.model.PostDao
import javax.inject.Singleton

private const val DATABASE_NAME = "appDataBase"

/**
 * Database module
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    /**
     * Provide post dao instance
     */
    @Provides
    fun providePostDao(appDataBase: AppDataBase): PostDao{
        return appDataBase.myModelDao()
    }

    /**
     * Provide App Database instance instance
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(appContext, AppDataBase::class.java, DATABASE_NAME).build()
    }
}