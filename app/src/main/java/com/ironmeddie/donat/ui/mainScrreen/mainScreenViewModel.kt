package com.ironmeddie.donat.ui.mainScrreen

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

    fun syncData(){
        viewModelScope.launch {

            Log.d("caheckCode", "viewmodel syncData")
            sync.invoke().collectLatest {
                Log.d("checkCode", "syncSuccess complited")
                _isPullRefreshing.value = false
            }
        }
    }

     private fun getData() {
        viewModelScope.launch {
            getTransactions().combine(getMoney()){ tr, mon ->
                Pair(tr,mon)
            }.combine(useCase()){ pair, cat->
                Pair(pair,cat)
            }.collectLatest {
                _currentMoney.value = it.first.second.money.toString()
                _transactions.value = it.first.first
                _categories.value = it.second
            }
        }
    }

    fun pullRefresh(){
        _isPullRefreshing.value = true
        syncData()
//        getData()

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