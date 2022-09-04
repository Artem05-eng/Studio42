package com.example.studio42.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.studio42.domain.entity.Employer
import com.example.studio42.domain.entity.RequestEmployer
import com.example.studio42.domain.usecase.DownloadDataUseCase
import com.example.studio42.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SecondViewModel @Inject constructor(
    private val downloadUseCase: DownloadDataUseCase
) : ViewModel() {
    private val mutableData = MutableLiveData<List<Employer>>()
    val data: LiveData<List<Employer>>
        get() = mutableData
    private val mutableFlagFilter = MutableLiveData<Boolean>(false)
    val flagFilter: LiveData<Boolean>
        get() = mutableFlagFilter
    private val mutableCount = MutableLiveData<Int>()
    val count: LiveData<Int>
        get() = mutableCount


    fun getEmploers(requestEmployer: RequestEmployer) = downloadUseCase(requestEmployer, mutableData, mutableCount)

    fun checkFlag() {
        mutableFlagFilter.postValue(true)
    }

    fun uncheckFlag() {
        mutableFlagFilter.postValue(false)
    }

}