package ytemplate.android.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ytemplate.android.core.database.POST_TABLE

/**
 * Post entity
 *
 * @property id
 * @property userId
 * @property title
 * @property body
 * @constructor Create empty Post entity
 */
@Entity(tableName = POST_TABLE)
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val userId: Int,
    val title: String,
    val body: String
)
