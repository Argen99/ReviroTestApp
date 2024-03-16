package com.example.revirotestapp.di

import com.example.revirotestapp.ui.fragments.MainViewModel
import com.example.revirotestapp.ui.fragments.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MainViewModel)
    viewModelOf(::SearchViewModel)
}