package com.github.mag0716.memorytraining

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Created by mag0716 on 2017/10/22.
 */
class DisposableTest {

    lateinit var compositeDisposable: CompositeDisposable

    @Before
    fun setup() {
        compositeDisposable = CompositeDisposable()
    }

    @After
    fun tearDown() {
        compositeDisposable.dispose()
    }

    // region clear

    @Test
    fun clearを実行すればsubscribeのonNextは呼ばれないことを確認() {
        var latestValue: Long? = null
        var isCompleted = false

        val subscriber = object : DisposableSubscriber<Long>() {
            override fun onNext(t: Long?) {
                t?.let { latestValue = it }
            }

            override fun onError(t: Throwable?) {
            }

            override fun onComplete() {
                isCompleted = true
            }

        }
        val disposable: Disposable = Flowable.interval(1, TimeUnit.SECONDS).subscribeWith(subscriber)
        compositeDisposable.add(disposable)


        Thread.sleep(1500)
        compositeDisposable.clear();
        Thread.sleep(1500)

        assertTrue(subscriber.isDisposed)
        assertThat(latestValue!!, `is`(0L))
        assertFalse(isCompleted)
    }

    @Test
    fun clearを実行すればsubscribeのonSuccessが呼ばれないことを確認() {
        var latestValue = 0L
        var isSucceed = false
        var isError = false

        val disposable = Single.just(1L)
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    latestValue = it
                    isSucceed = true
                }, { isError = true })
        compositeDisposable.add(disposable)

        Thread.sleep(1500)
        compositeDisposable.clear()
        Thread.sleep(1500)

        assertTrue(disposable.isDisposed)
        assertThat(latestValue, `is`(0L))
        assertFalse(isSucceed)
        assertFalse(isError)
    }

    // endregion
}