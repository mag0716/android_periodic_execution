package com.github.mag0716.memorytraining.service

import com.github.mag0716.memorytraining.R
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by mag0716 on 2017/11/08.
 */
@Config(manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner::class)
class TaskRegisterTypeTest {

    // region getRegisterNameList

    @Test
    fun getRegisterNameList_TaskRegister登録済みの名前の一覧が返却されること() {
        val list = TaskRegisterType.getRegisterNameList(RuntimeEnvironment.application)
        assertThat(list.size, `is`(2))
        assertThat(list[0], `is`(RuntimeEnvironment.application.getString(R.string.setting_api_gcmnetworkmanager_name)))
        assertThat(list[1], `is`(RuntimeEnvironment.application.getString(R.string.setting_api_alarmmanager_name)))
    }

    // endregion
}