package com.github.mag0716.memorytraining.service

import com.github.mag0716.memorytraining.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.gms.common.ShadowGoogleApiAvailability


/**
 * Created by mag0716 on 2017/11/08.
 */
@Config(manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner::class)
class TaskRegisterTypeTest {

    @Before
    fun setUp() {
    }

    // region getRegisterNameList

    @Test
    fun getRegisterNameList_GooglePlayServicesが利用可能であれば全てのTaskRegisterの一覧が返却されること() {
        val shadowGoogleApiAvailability: ShadowGoogleApiAvailability = Shadow.extract(GoogleApiAvailability.getInstance())
        val expectedCode = ConnectionResult.SUCCESS
        shadowGoogleApiAvailability.setIsGooglePlayServicesAvailable(expectedCode)

        val list = TaskRegisterType.getRegisterNameList(RuntimeEnvironment.application)
        assertThat(list.size, `is`(2))
        assertThat(list[0], `is`(RuntimeEnvironment.application.getString(R.string.setting_api_gcmnetworkmanager_name)))
        assertThat(list[1], `is`(RuntimeEnvironment.application.getString(R.string.setting_api_alarmmanager_name)))
    }

    @Test
    fun getRegisterNameList_GooglePlayServicesが利用不可能であればGooglePlayServicesが不要のTaskRegisterの一覧が返却されること() {
        val shadowGoogleApiAvailability: ShadowGoogleApiAvailability = Shadow.extract(GoogleApiAvailability.getInstance())
        val expectedCode = ConnectionResult.SERVICE_DISABLED
        shadowGoogleApiAvailability.setIsGooglePlayServicesAvailable(expectedCode)

        val list = TaskRegisterType.getRegisterNameList(RuntimeEnvironment.application)
        assertThat(list.size, `is`(1))
        assertThat(list[0], `is`(RuntimeEnvironment.application.getString(R.string.setting_api_alarmmanager_name)))
    }

    // endregion
}