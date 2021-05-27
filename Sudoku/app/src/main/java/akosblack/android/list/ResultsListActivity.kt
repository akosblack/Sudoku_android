package akosblack.android.list

import akosblack.android.R
import akosblack.android.adapter.SimpleItemRecyclerViewAdapter
import akosblack.android.model.Result
import akosblack.android.viewmodel.ResultsViewModel
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.result_list.*


class ResultsListActivity : AppCompatActivity(),  ResultCreateFragment.ResultCreatedListener,
    SimpleItemRecyclerViewAdapter.ResultItemClickListener {

    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter

    private lateinit var resultsViewModel: ResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_list)

        setupRecyclerView()

        resultsViewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)
        resultsViewModel.allResults.observe(this) { results ->
            simpleItemRecyclerViewAdapter.addAll(results)
        }

    }

    private fun setupRecyclerView() {
        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
        simpleItemRecyclerViewAdapter.itemClickListener = this
        rvResultList.adapter = simpleItemRecyclerViewAdapter
    }

    override fun onItemLongClick(position: Int, view: View, result: Result): Boolean {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_result)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    resultsViewModel.delete(result)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }

    override fun onResultCreated(result: Result) {
        resultsViewModel.insert(result)
    }

}
