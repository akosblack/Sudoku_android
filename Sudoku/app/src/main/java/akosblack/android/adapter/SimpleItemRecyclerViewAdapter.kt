package akosblack.android.adapter

import akosblack.android.R
import akosblack.android.model.Result
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_result.view.*


class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val mutableList = mutableListOf<Result>()

    var itemClickListener: ResultItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = mutableList[position]

        holder.result = result

        holder.tvUserName.text = result.userName
        holder.tvWinTime.text = result.winTime

        val resource = when (result.userIcon) {
            Result.ICON.ICON1 -> R.drawable.user_icon_1
            Result.ICON.ICON2 -> R.drawable.user_icon_2
            Result.ICON.ICON3 -> R.drawable.user_icon_3
        }
        holder.ivUserIcon.setImageResource(resource)
    }

    fun addAll(result: List<Result>) {
        mutableList.clear()
        mutableList.addAll(result)
        notifyDataSetChanged()
    }

    override fun getItemCount() = mutableList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvWinTime: TextView = itemView.tvWinTime
        val tvUserName: TextView = itemView.tvUserName
        val ivUserIcon: ImageView = itemView.ivUserIcon

        var result: Result? = null

        init {
            itemView.setOnLongClickListener{ view ->
                result?.let {result -> itemClickListener?.onItemLongClick(adapterPosition, view, result) }
                true
            }
        }
    }

    interface ResultItemClickListener{
        fun onItemLongClick(position: Int, view: View, result: Result): Boolean
    }

}