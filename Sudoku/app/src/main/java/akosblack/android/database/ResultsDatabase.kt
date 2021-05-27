package akosblack.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 2,
    exportSchema = false,
    entities = [RoomResults::class]
)
@TypeConverters(
    ResultTypeConverter::class
)


abstract class ResultsDatabase : RoomDatabase() {

    abstract fun resultsDao(): ResultsDao

}