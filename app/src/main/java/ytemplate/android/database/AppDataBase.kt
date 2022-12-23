package ytemplate.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ytemplate.android.database.model.MyModel
import ytemplate.android.database.model.MyModelDao

@Database(entities = [MyModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun myModelDao(): MyModelDao
}