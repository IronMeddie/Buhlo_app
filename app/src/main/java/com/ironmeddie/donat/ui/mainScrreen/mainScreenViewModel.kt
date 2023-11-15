package com.ironmeddie.donat.ui.mainScrreen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.donat.domain.SyncDataUseCase
import com.ironmeddie.donat.domain.getMainScreenData.getCategoriesUseCase
import com.ironmeddie.donat.domain.getMainScreenData.getCurrentmoney
import com.ironmeddie.donat.domain.getMainScreenData.getTransaction
import com.ironmeddie.donat.domain.getMainScreenData.updateMoneyValue
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.models.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val useCase: getCategoriesUseCase,
    private val updateMoneyValue: updateMoneyValue,
    private val getMoney: getCurrentmoney,
    private val getTransactions: getTransaction,
    private val sync: SyncDataUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow(listOf<Category>())
    val categories = _categories.asStateFlow()

    private val _transactions = MutableStateFlow(listOf<Transaction>())
    val transactions = _transactions.asStateFlow()

//    private val _currentcategory = MutableStateFlow(mutableListOf<Category>())
//    val currentcategory = _currentcategory.asStateFlow()

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

    fun syncData() {
        _isPullRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            sync().collectLatest {
                _isPullRefreshing.value = false
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            getTransactions().combine(getMoney()) { tr, mon ->
                Pair(tr, mon)
            }.combine(useCase()) { pair, cat ->
                Pair(pair, cat)
            }.collectLatest {
                _currentMoney.value = it.first.second.money.toString()
                _transactions.value = it.first.first
                _categories.value = it.second
            }
        }
    }

    private var job: Job? = null

    fun getCategories(str: String) {
        job = null
        job = viewModelScope.launch {
            useCase(str).collect() {
                _categories.value = it
                job = null
            }
        }


    }


    fun updateMoney() {
        viewModelScope.launch {
            val categories = categories.value.filter { it.isChoosed == true }
            updateMoneyValue(summ.value.toDouble(), categories)
        }
    }

    fun changeCategory(category: Category) {
        _categories.value = categories.value.map { if (it.id == category.id) it.copy(isChoosed = !it.isChoosed) else it }
    }
    fun clearAllCategories() {
        _categories.value = categories.value.map { it.copy(isChoosed = false)  }
    }

    fun search(str: String) {
        _search.value = str
        getCategories(str)
    }

    fun summ(str: String) {
        if (str.isDigitsOnly())
            _summ.value = str
    }
}