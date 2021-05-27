package akosblack.android.repostitory

import akosblack.android.database.ResultsDao
import akosblack.android.database.RoomResults
import akosblack.android.model.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val resultsDao: ResultsDao) {

    fun getAllTodos(): LiveData<List<Result>> {
        return resultsDao.getAllResults()
            .map {roomResults ->
                roomResults.map {roomResults ->
                    roomResults.toDomainModel() }
            }
    }

    suspend fun insert(result: Result) = withContext(Dispatchers.IO) {
        resultsDao.insertResult(result.toRoomModel())
    }

    suspend fun delete(result: Result) = withContext(Dispatchers.IO) {
        val roomResult = resultsDao.getResultById(result.id) ?: return@withContext
        resultsDao.deleteResult(roomResult)
    }

    private fun RoomResults.toDomainModel(): Result {
        return Result(
            id = id,
            userName = userName,
            winTime = winTime,
            userIcon = userIcon
        )
    }

    private fun Result.toRoomModel(): RoomResults {
        return RoomResults(
            userName = userName,
            winTime = winTime,
            userIcon = userIcon
        )
    }
}