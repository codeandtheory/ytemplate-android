package ytemplate.android.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ytemplate.android.database.model.MyModelDao
import javax.inject.Singleton

private const val DATABASE_NAME = "appDataBase"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideMyModelDao(appDataBase: AppDataBase): MyModelDao{
        return appDataBase.myModelDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(appContext, AppDataBase::class.java, DATABASE_NAME).build()
    }
}