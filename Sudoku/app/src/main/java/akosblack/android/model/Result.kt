package akosblack.android.model

class Result(
    val id: Long? = null,
    val userName: String,
    val winTime: String,
    val userIcon: ICON
){

    enum class ICON {
        ICON1, ICON2, ICON3
    }

}