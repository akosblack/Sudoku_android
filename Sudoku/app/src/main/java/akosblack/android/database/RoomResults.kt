package akosblack.android.database

import akosblack.android.model.Result
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class RoomResults(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userName: String,
    val winTime: String,
    val userIcon: Result.ICON
)