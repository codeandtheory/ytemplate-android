package ytemplate.android.core.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ytemplate.android.core.database.DATABASE_VERSION
import ytemplate.android.core.database.dao.PostDao
import ytemplate.android.core.database.model.PostEntity

/**
 * App data base
 *
 * @constructor Create empty App data base
 */
@Database(entities = [PostEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    /**
     * My model dao
     *
     * @return
     */
    abstract fun myModelDao(): PostDao
}
