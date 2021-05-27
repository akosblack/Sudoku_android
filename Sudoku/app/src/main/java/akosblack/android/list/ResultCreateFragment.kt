package akosblack.android.list

import akosblack.android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import akosblack.android.model.Result
import akosblack.android.model.SudokuModel
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_create.*


class ResultCreateFragment : DialogFragment(){

    private lateinit var listener: ResultCreatedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as ResultCreatedListener
            } else {
                activity as ResultCreatedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        dialog?.setTitle("Kérem válasszon!")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spnrUserIcon.adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            listOf("Samurai","Shinobi","Ninja")
        )

        tvWinTime.text = SudokuModel.winnedTimeWhenFinished

        btnCreateResult.setOnClickListener {
            val selectedIcon = when (spnrUserIcon.selectedItemPosition) {
                0 -> Result.ICON.ICON1
                1 -> Result.ICON.ICON2
                2 -> Result.ICON.ICON3
                else -> Result.ICON.ICON1
            }

            listener.onResultCreated(
                Result(
                    userName = etUserName.text.toString(),
                    winTime = SudokuModel.winnedTimeWhenFinished,
                    userIcon = selectedIcon
                )
            )
            dismiss()
        }

        btnCancelCreateResult.setOnClickListener {
            dismiss()
        }

    }


    interface ResultCreatedListener {
        fun onResultCreated(result: Result)
    }

}