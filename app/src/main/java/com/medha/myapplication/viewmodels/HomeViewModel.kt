package com.medha.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.medha.myapplication.repositories.HomePageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomePageRepository,
    application: Application
) : AndroidViewModel(application) {

    fun getDashboardData(url:String) = repository.getWalletBalnce(url)
}