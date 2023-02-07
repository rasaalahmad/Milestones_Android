package com.example.milestone2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.milestone2.api_resources.MemeAPI
import com.example.milestone2.api_resources.MemeAPIClient
import com.example.milestone2.api_resources.MemeAPIEndpointInterface
import com.example.milestone2.viewmodels.MemeViewModel
import com.example.milestone2.data_classes.Data
import com.example.milestone2.data_classes.Meme
import com.example.milestone2.repository.MemeRepository
import com.google.common.truth.ExpectFailure.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MemeAPIUnitTest {
    @InjectMocks
    private lateinit var memeRepository: MemeRepository
    @Mock
    private lateinit var memeViewModel: MemeViewModel
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val memeAPIEndpointInterface: MemeAPIEndpointInterface? = MemeAPI.getInstance().create(MemeAPIEndpointInterface::class.java)

    @Before
    fun init(){
        MockitoAnnotations.openMocks(this)
        memeViewModel = MemeViewModel(memeRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMemesResponseTest()= runTest {
        val response =  memeAPIEndpointInterface!!.getMemeData().execute()
        assert(response.isSuccessful)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMemesCompare() =  runTest{
      assert(memeRepository.memeAPIClient.getMemesData().value == memeViewModel.getMemes().value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMemesResponseValidation()= runTest {
        val response = memeAPIEndpointInterface?.getMemeData()?.execute()?.body()?.data?.memes
        assert(response?.size == 100)
    }
}