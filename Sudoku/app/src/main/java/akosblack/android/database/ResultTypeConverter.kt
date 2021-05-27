package akosblack.android.database

import akosblack.android.model.Result
import androidx.room.TypeConverter

class ResultTypeConverter {

    companion object {
        const val ICON1 = "ICON1"
        const val ICON2 = "ICON2"
        const val ICON3 = "ICON3"
    }

    @TypeConverter
    fun toIcon(value: String?): Result.ICON {
        return when (value) {
            ICON1 -> Result.ICON.ICON1
            ICON2 -> Result.ICON.ICON2
            ICON3 -> Result.ICON.ICON3
            else -> Result.ICON.ICON1
        }
    }

    @TypeConverter
    fun toString(enumValue: Result.ICON): String? {
        return enumValue.name
    }

}