package akosblack.android.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResultsDao {

    @Insert
    fun insertResult(result: RoomResults)

    @Query("SELECT * FROM results")
    fun getAllResults(): LiveData<List<RoomResults>>

    @Query("SELECT * FROM results WHERE id == :id")
    fun getResultById(id: Long?): RoomResults?

    @Update
    fun updateResult(result: RoomResults): Int

    @Delete
    fun deleteResult(result: RoomResults)

}