package ytemplate.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ytemplate.android.database.model.Post
import ytemplate.android.database.model.PostDao

@Database(entities = [Post::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun myModelDao(): PostDao
}