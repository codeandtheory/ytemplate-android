package ytemplate.android.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ytemplate.android.core.database.DATABASE_NAME
import ytemplate.android.core.database.dao.PostDao
import ytemplate.android.core.database.room.AppDataBase
import javax.inject.Singleton

/**
 * Database module
 *
 * @constructor Create empty Database module
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    /**
     * Provide post dao
     *
     * @param appDataBase
     * @return
     */
    @Provides
    fun providePostDao(appDataBase: AppDataBase): PostDao {
        return appDataBase.myModelDao()
    }

    /**
     * Provide app database
     * @param appContext
     * @return
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(appContext, AppDataBase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
    }
}
