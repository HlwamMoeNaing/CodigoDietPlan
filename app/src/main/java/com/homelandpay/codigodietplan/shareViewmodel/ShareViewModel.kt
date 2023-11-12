package com.homelandpay.codigodietplan.shareViewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homelandpay.codigodietplan.data.modal.HealthConcernEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor() :ViewModel() {
    val progressStatus: MutableLiveData<Int> = MutableLiveData()
}