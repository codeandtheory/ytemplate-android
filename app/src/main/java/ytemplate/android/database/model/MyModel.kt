package ytemplate.android.database.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class MyModel(val name:String){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

@Dao
interface MyModelDao {
    @Query("SELECT * FROM mymodel")
   fun getMyModels(): Flow<List<MyModel>>

   @Insert
   suspend fun insert(model: MyModel)
}