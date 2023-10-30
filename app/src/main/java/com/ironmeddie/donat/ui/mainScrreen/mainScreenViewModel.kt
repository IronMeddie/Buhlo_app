package com.ironmeddie.donat.ui.mainScrreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.donat.domain.getMainScreenData.getCategoriesUseCase
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.utils.Constance
import com.ironmeddie.donat.utils.di
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainScreenViewModel(private val useCase: getCategoriesUseCase = di.getMaiScreenUseCases()
): ViewModel() {

    private val _categories = MutableStateFlow(listOf<Category>())
    val categories = _categories.asStateFlow()

    private val _currentcategory = MutableStateFlow(Category())
    val currentcategory = _currentcategory.asStateFlow()

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    init {
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            useCase().collectLatest {
                _categories.value = it
                Log.d(Constance.TAG, "загружено")
            }
        }
    }

    fun changeCategory(category: Category){
        _currentcategory.value = category
    }
    fun search(str: String){
        _search.value = str
    }
}