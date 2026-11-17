package com.example.mdp

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(
        ::GameViewModel
    )
}