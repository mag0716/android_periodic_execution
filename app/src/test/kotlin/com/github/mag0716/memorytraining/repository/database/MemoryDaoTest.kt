package com.github.mag0716.memorytraining.repository.database

import android.arch.persistence.room.Room
import com.github.mag0716.memorytraining.model.Memory
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
/* ktlint-disable no-wildcard-imports */
import java.util.*
/* ktlint-disable no-wildcard-imports */
import java.util.concurrent.TimeUnit

/**
 * Created by mag0716 on 2017/06/28.
 */
@RunWith(RobolectricTestRunner::class)
class MemoryDaoTest {

    val TIMEOUT = 5L

    lateinit var db: ApplicationDatabase
    lateinit var dao: MemoryDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, ApplicationDatabase::class.java).build()
        dao = db.memoryDao()

        val testSubscriber = TestSubscriber<List<Memory>>()
        Completable.create { emitter ->
            val memoryList = dao.loadAll()
            if (memoryList.isEmpty()) {
                dao.insertAll(Arrays.asList(
                        Memory(0, "question0", "answer0", 0, 0, 0L),
                        Memory(1, "question1", "answer1", 1, 1, 1L),
                        Memory(2, "question2", "answer2", 2, 2, 2L)
                ))
            }
            emitter.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .toFlowable<List<Memory>>()
                .subscribe(testSubscriber)
        testSubscriber.await(5, TimeUnit.SECONDS)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun テストデータが登録されていること() {
        val testSubscriber = TestSubscriber<List<Memory>>()
        Single.create { emitter: SingleEmitter<List<Memory>>? ->
            if (emitter != null) {
                val memoryList = dao.loadAll()
                if (memoryList != null) {
                    emitter.onSuccess(memoryList)
                } else {
                    emitter.onError(IllegalStateException("not matched Memory"))
                }
            }
        }.subscribeOn(Schedulers.io())
                .toFlowable()
                .subscribe(testSubscriber)
        testSubscriber.await(TIMEOUT, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors()
        assertThat(testSubscriber.events.size, `is`(3))
    }

    // region loadRecent

    @Test
    fun 直近のMemoryが取得できること() {
        val testSubscriber = TestSubscriber<Memory>()
        Single.create { emitter: SingleEmitter<Memory>? ->
            if (emitter != null) {
                val memory = dao.loadRecent(0)
                if (memory != null) {
                    emitter.onSuccess(memory)
                } else {
                    emitter.onError(IllegalStateException("not matched Memory"))
                }
            }
        }.subscribeOn(Schedulers.io())
                .toFlowable()
                .subscribe(testSubscriber)
        testSubscriber.await(TIMEOUT, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors()
        val memory = testSubscriber.events[0].get(0) as Memory
        assertThat(memory.id, `is`(1L))
    }

    @Test
    fun 指定した日時以降のデータがない場合は取得できないこと() {
        val testSubscriber = TestSubscriber<Memory>()
        Single.create { emitter: SingleEmitter<Memory>? ->
            if (emitter != null) {
                val memory = dao.loadRecent(3)
                if (memory != null) {
                    emitter.onSuccess(memory)
                } else {
                    emitter.onError(IllegalStateException("not matched Memory"))
                }
            }
        }.subscribeOn(Schedulers.io())
                .toFlowable()
                .subscribe(testSubscriber)
        testSubscriber.await(TIMEOUT, TimeUnit.SECONDS)

        testSubscriber.assertError(IllegalStateException::class.java)
    }
    // endregion
}