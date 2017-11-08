package com.github.mag0716.memorytraining.presenter

import android.content.Context
import com.github.mag0716.memorytraining.model.Memory
import com.github.mag0716.memorytraining.repository.database.MemoryDao
import com.github.mag0716.memorytraining.service.ITaskRegister
import com.github.mag0716.memorytraining.service.TaskConductor
import com.github.mag0716.memorytraining.view.ListView
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by mag0716 on 2017/09/27.
 */
@Config(manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner::class)
class ListPresenterTest {

    class MockView : ListView {
        var memoryList: MutableList<Memory>? = null
        var editMemory: Long = 0
        var dismissMemory: Long = 0
        var completedTraining: Boolean = false

        override fun getContext(): Context {
            return RuntimeEnvironment.application
        }

        override fun showMemoryList(memoryList: MutableList<Memory>) {
            this.memoryList = memoryList
        }

        override fun editMemory(id: Long) {
            editMemory = id
        }

        override fun dismissMemory(id: Long) {
            dismissMemory = id
        }

        override fun completedTraining() {
            completedTraining = true
        }
    }

    class MockDao : MemoryDao {

        val list = mutableListOf(
                Memory(1L, "question1", "answer1", 1, 1, 1, 1),
                Memory(2L, "question2", "answer2", 2, 2, 2, 2),
                Memory(3L, "question3", "answer3", 3, 3, 3, 3))

        var loadCount: Int = 0

        override fun loadAll(): MutableList<Memory> {
            loadCount++
            return list
        }

        override fun loadAll(trainingDatetime: Long): MutableList<Memory> {
            loadCount++
            return list.filter { memory -> memory.nextTrainingDatetime <= trainingDatetime }
                    .toMutableList()
        }

        override fun loadRecent(trainingDatetime: Long): Memory {
            loadCount++
            return list.filter { memory -> memory.nextTrainingDatetime <= trainingDatetime }
                    .first()
        }

        override fun load(id: Long): Memory? {
            loadCount++
            return list.filter { memory -> memory.id == id }
                    .firstOrNull()
        }

        override fun insertAll(memoryList: MutableList<Memory>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun insert(memory: Memory) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun update(memory: Memory): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun delete(memory: Memory) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class MockTaskRegister : ITaskRegister {

        var registeredMemory: Memory? = null

        override fun registerTask(context: Context, memory: Memory) {
            registeredMemory = memory
        }

        override fun getName(context: Context): String {
            return "name"
        }

        override fun getDescription(context: Context): String {
            return "description"
        }

        override fun isAvailable(context: Context): Boolean {
            return true
        }

        override fun isResolvable(context: Context): Boolean {
            return true;
        }
    }

    lateinit var presenter: ListPresenter
    lateinit var memoryDao: MockDao
    lateinit var view: MockView

    @Before
    fun setup() {
        view = MockView()
        memoryDao = MockDao()
        presenter = ListPresenter(memoryDao,
                TaskConductor(RuntimeEnvironment.application, memoryDao, MockTaskRegister()))
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    // TODO: add tests

    @Test
    fun test() {
    }

}