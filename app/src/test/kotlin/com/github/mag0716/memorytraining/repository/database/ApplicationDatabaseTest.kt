package com.github.mag0716.memorytraining.repository.database

import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import android.arch.persistence.room.testing.MigrationTestHelper
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by mag0716 on 2017/06/06.
 */
@Config(manifest = "src/main/AndroidManifest.xml", assetDir = "../../schemas")
@RunWith(RobolectricTestRunner::class)
class ApplicationDatabaseTest {

    private val DB_NAME = "migrate_test"

    @Rule
    @JvmField
    var helper = MigrationTestHelper(RuntimeEnvironment.application,
            ApplicationDatabase::
            class.java.canonicalName,
            FrameworkSQLiteOpenHelperFactory())


    @Test
    fun v1() {
        val db = helper.createDatabase(DB_NAME, 1)

        db.execSQL("INSERT INTO Memory values(0,'question0','answer0',0,0,0);")
        db.execSQL("INSERT INTO Memory values(1,'question1','answer1',1,1,1);")
        db.execSQL("INSERT INTO Memory values(2,'question2','answer2',2,2,2);")

        db.query("SELECT * FROM Memory;").use {
            cursor ->
            {
                var index = 0
                while (cursor.moveToNext()) {
                    assertThat(cursor.getInt(cursor.getColumnIndex("id")), `is`(index))
                    assertThat(cursor.getString(cursor.getColumnIndex("question")), `is`("question%d".format(index)))
                    assertThat(cursor.getString(cursor.getColumnIndex("answer")), `is`("answer%d".format(index)))
                    assertThat(cursor.getInt(cursor.getColumnIndex("level")), `is`(index))
                    assertThat(cursor.getInt(cursor.getColumnIndex("count")), `is`(index))
                    assertThat(cursor.getLong(cursor.getColumnIndex("next_training_datetime")), `is`(index.toLong()))
                    index++
                }
            }
        }

        db.close()
    }
}