package com.zoroxnekko.cryptotracker.di

import com.zoroxnekko.cryptotracker.core.data.networking.HttpClientFactory
import com.zoroxnekko.cryptotracker.crypto.data.remote_data_source.RemoteCoinDataSource
import com.zoroxnekko.cryptotracker.crypto.domain.CoinDataSource
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}