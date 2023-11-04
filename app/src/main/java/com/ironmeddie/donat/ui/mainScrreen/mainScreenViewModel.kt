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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val useCase: getCategoriesUseCase,
    private val getMoney: getCurrentmoney,
    private val updateMoneyValue: updateMoneyValue
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
                _currentMoney.value = it.money.toString()
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

            updateMoneyValue(summ.value.toDouble(), listOf(currentcategory.value) )
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