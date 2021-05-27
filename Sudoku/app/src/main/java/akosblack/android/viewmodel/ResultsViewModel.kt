package akosblack.android.viewmodel

import akosblack.android.MainActivity
import akosblack.android.repostitory.Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import akosblack.android.model.Result

class ResultsViewModel : ViewModel() {

    private val repository: Repository

    val allResults: LiveData<List<Result>>

    init {
        val todoDao = MainActivity.Companion.resultsDatabase.resultsDao()
        repository = Repository(todoDao)
        allResults = repository.getAllTodos()
    }

    fun insert(result: Result) = viewModelScope.launch {
        repository.insert(result)
    }

    fun delete(result: Result) = viewModelScope.launch {
        repository.delete(result)
    }
}