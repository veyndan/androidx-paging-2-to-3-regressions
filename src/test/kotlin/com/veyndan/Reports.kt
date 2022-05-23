package com.veyndan

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.paging.RxPagedListBuilder
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class Reports {

  // https://issuetracker.google.com/issues/233525225
  @Test
  fun `varying emission count from RxPagedListBuilder`() {
    class QueryDataSourceFactory : DataSource.Factory<Int, String>() {
      override fun create(): PositionalDataSource<String> = object : PositionalDataSource<String>() {
        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<String>) {
          callback.onResult(listOf("hello", "world"), 0, 2)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<String>) {
          TODO()
        }
      }
    }

    RxPagedListBuilder(QueryDataSourceFactory(), 10)
      .setFetchScheduler(Schedulers.trampoline())
      .setNotifyScheduler(Schedulers.trampoline())
      .buildObservable()
      .test()
      .assertValueCount(1)
  }

  // https://issuetracker.google.com/issues/233703110
  @Test
  fun `error no longer in rx chain from RxPagedListBuilder`() {
    class DodgyQueryDataSourceFactory : DataSource.Factory<Int, String>() {
      override fun create(): PositionalDataSource<String> = object : PositionalDataSource<String>() {
        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<String>) {
          error("This was totally unexpected.")
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<String>) {
          TODO()
        }
      }
    }

    RxPagedListBuilder(DodgyQueryDataSourceFactory(), 10)
      .setFetchScheduler(Schedulers.trampoline())
      .setNotifyScheduler(Schedulers.trampoline())
      .buildObservable()
      .test()
      .assertErrorMessage("This was totally unexpected.")
  }
}
