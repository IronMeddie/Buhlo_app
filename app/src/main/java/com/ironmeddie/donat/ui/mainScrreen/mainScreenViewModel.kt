package com.ironmeddie.donat.ui.mainScrreen

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.donat.domain.getMainScreenData.getCategoriesUseCase
import com.ironmeddie.donat.domain.getMainScreenData.getCurrentmoney
import com.ironmeddie.donat.domain.getMainScreenData.updateMoneyValue
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.utils.Constance
import com.ironmeddie.donat.utils.di
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val useCase: getCategoriesUseCase = di.getMaiScreenUseCases(),
    private val getMoney: getCurrentmoney = di.getCurrentMoney(),
    private val updateMoneyValue: updateMoneyValue = di.updateMoneyUseCase()
) : ViewModel() {

    private val _categories = MutableStateFlow(listOf<Category>())
    val categories = _categories.asStateFlow()

    private val _currentcategory = MutableStateFlow(Category())
    val currentcategory = _currentcategory.asStateFlow()

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    private val _summ = MutableStateFlow("")
    val summ = _summ.asStateFlow()

    private val _currentMoney = MutableStateFlow("")
    val currentMoney = _currentMoney.asStateFlow()

    private val _isPullRefreshing = MutableStateFlow(false)
    val isPullRefreshing = _isPullRefreshing.asStateFlow()

    init {
        getData()
    }

     private fun getData() {
        viewModelScope.launch {
            useCase().collectLatest {
                _categories.value = it
                Log.d(Constance.TAG, "загружено")

            }
            getMoney().collectLatest {
                _currentMoney.value = it
                _isPullRefreshing.value = false
            }
        }
    }

    fun pullRefresh(){
        _isPullRefreshing.value = true
        getData()
    }

    fun updateMoney(){
        viewModelScope.launch {
            updateMoneyValue(summ.value.toDouble())
        }
    }
    fun changeCategory(category: Category) {
        _currentcategory.value = category
    }

    fun search(str: String) {
        _search.value = str
    }

    fun summ(str: String) {
        if (str.isDigitsOnly())
            _summ.value = str
    }
}