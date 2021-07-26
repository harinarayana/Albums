package com.coding.codingapplication

import android.os.Build
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.coding.codingapplication.api.APIService
import com.coding.codingapplication.data.Repository
import com.coding.codingapplication.db.AppDatabase
import com.coding.codingapplication.ui.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalPagingApi
@Config(sdk = [Build.VERSION_CODES.Q])
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ExampleUnitTest {
    private var recyclerView: RecyclerView? = null
    private var viewModel: MainViewModel? = null

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        recyclerView = mock(RecyclerView::class.java)
        val api = mock(APIService::class.java)
        val db = mock(AppDatabase::class.java)
        val repo = Repository(api, db)
        viewModel = MainViewModel(repo)

    }

    //ToDO unit test screen navigation through robolectric
    /*@Test
    fun testMockScreenNavigation(){

    }*/

    @Test
    @Throws(Exception::class)
    fun viewModelShouldNotBeNull() {
        Assert.assertNotNull(viewModel)
    }

    @Test
    fun testSetAdapterNull() {
        recyclerView?.setAdapter(null)
        verify(recyclerView)?.setAdapter(null)
    }

    @Test
    fun testScrollTo() {
        `when`(recyclerView?.getScrollX())?.thenReturn(10)
        `when`(recyclerView?.getScrollY()).thenReturn(20)
        recyclerView?.scrollTo(15, 25)
    }

}