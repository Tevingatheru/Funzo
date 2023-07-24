package com.learner.funzo

import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewMainActivityTest {

    // Mock FirebaseAuth instance
    private lateinit var mockedAuth: FirebaseAuth

    // The MainActivity instance to be tested
    private lateinit var mainActivity: MainActivity

    @Before
    fun setup() {
        // Initialize the mocked FirebaseAuth instance
        mockedAuth = mock(FirebaseAuth::class.java)
        mainActivity = MainActivity()
        mainActivity.auth = mockedAuth
    }

    @Test
    fun testLogout() {
        // Call the logout function
        mainActivity.logout()

        // Verify that FirebaseAuths signOut() method was called
        verify(mockedAuth).signOut()

        // Verify that the MainActivity was finished
        assertEquals(mainActivity.isFinishing, true)
    }
}


class MainActivityTest