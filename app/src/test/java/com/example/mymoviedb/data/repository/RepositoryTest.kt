package com.example.mymoviedb.data.repository

import com.example.mymoviedb.data.datastore.UserDataStoreManager
import com.example.mymoviedb.data.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.data.listmovie.GetPopularMovieResponse
import com.example.mymoviedb.data.service.ApiHelper
import com.example.mymoviedb.data.service.ApiService
import com.example.mymoviedb.data.userdatabase.DbHelper
import com.example.mymoviedb.data.userdatabase.User
import com.example.mymoviedb.data.userdatabase.UserFavorite
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

class RepositoryTest {
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var dbHelper: DbHelper
    private lateinit var repository: Repository
    private lateinit var dataStoreManager: UserDataStoreManager

    @Before
    fun setUp() {
        apiService = mockk()
        dbHelper = mockk()
        dataStoreManager = mockk()
        apiHelper = ApiHelper(apiService)
        repository = Repository(apiHelper, dbHelper, dataStoreManager)
    }

    @Test
    fun getDetailMovie(): Unit = runBlocking {
        val respDetailMovie = mockk<GetDetailMovieResponse>()
        val movieId = 1
        every {
            runBlocking {
                apiHelper.getDetailMovie(movieId)
            }
        } returns respDetailMovie
        repository.getDetailMovie(movieId)
        verify {
            runBlocking { apiHelper.getDetailMovie(movieId) }
        }
    }

    @Test
    fun getMovie(): Unit = runBlocking {
        val respPopularMovie = mockk<GetPopularMovieResponse>()
        every {
            runBlocking {
                apiHelper.getAllPopularMovie()
            }
        } returns respPopularMovie
        repository.getMovie()
        verify {
            runBlocking { apiHelper.getAllPopularMovie() }
        }
    }

    @Test
    fun insertUser(): Unit = runBlocking {
        val inputUser = mockk<User>()
        val respInsert = 1L
        every {
            runBlocking {
                dbHelper.insertUser(inputUser)
            }
        } returns respInsert
        repository.insertUser(inputUser)
        verify {
            runBlocking { dbHelper.insertUser(inputUser) }
        }
    }

    @Test
    fun updateUser(): Unit = runBlocking {
        val updateUser = mockk<User>()
        val respInsert = 1
        every {
            runBlocking {
                dbHelper.updateUser(updateUser)
            }
        } returns respInsert
        repository.updateUser(updateUser)
        verify {
            runBlocking { dbHelper.updateUser(updateUser) }
        }
    }

    @Test
    fun verifyLogin(): Unit = runBlocking {
        val respUser = mockk<List<User>>()
        val emailInput = ""
        val passwordInput = ""
        every {
            runBlocking {
                dbHelper.verifyLogin(emailInput, passwordInput)
            }
        } returns respUser
        repository.verifyLogin(emailInput, passwordInput)
        verify {
            runBlocking { dbHelper.verifyLogin(emailInput, passwordInput) }
        }
    }

    @Test
    fun checkEmail(): Unit = runBlocking {
        val respUser = mockk<List<User>>()
        val emailInput = ""
        every {
            runBlocking {
                dbHelper.checkEmail(emailInput)
            }
        } returns respUser
        repository.checkEmail(emailInput)
        verify {
            runBlocking { dbHelper.checkEmail(emailInput) }
        }
    }

    @Test
    fun insertFavorite(): Unit = runBlocking {
        val respInsert = 1L
        val inputFavorite = mockk<UserFavorite>()
        every {
            runBlocking {
                dbHelper.insertFavorite(inputFavorite)
            }
        } returns respInsert
        repository.insertFavorite(inputFavorite)
        verify {
            runBlocking { dbHelper.insertFavorite(inputFavorite) }
        }
    }

    @Test
    fun deleteFavorite(): Unit = runBlocking {
        val respDelete = 1
        val inputFavorite = mockk<UserFavorite>()
        every {
            runBlocking {
                dbHelper.deleteFavorite(inputFavorite)
            }
        } returns respDelete
        repository.deleteFavorite(inputFavorite)
        verify {
            runBlocking { dbHelper.deleteFavorite(inputFavorite) }
        }
    }

    @Test
    fun getFavorite(): Unit = runBlocking {
        val respFavorite = mockk<List<UserFavorite>>()
        val inputEmail = ""
        every {
            runBlocking {
                dbHelper.getAllFavorite(inputEmail)
            }
        } returns respFavorite
        repository.getFavorite(inputEmail)
        verify {
            runBlocking { dbHelper.getAllFavorite(inputEmail) }
        }
    }

    @Test
    fun addFavorite(): Unit = runBlocking {
        val respFavorite = mockk<List<UserFavorite>>()
        val inputUser = ""
        val inputMovieId = 1
        every {
            runBlocking {
                dbHelper.favorite(inputUser, inputMovieId)
            }
        } returns respFavorite
        repository.isFavorite(inputUser, inputMovieId)
        verify {
            runBlocking { dbHelper.favorite(inputUser, inputMovieId) }
        }
    }

    @Test
    fun setUser(): Unit = runBlocking {
        val inputEmail = ""
        val inputUsername = ""
        every {
            runBlocking {
                dataStoreManager.setUser(inputEmail, inputUsername)
            }
        } returns any()
        repository.setUser(inputEmail, inputUsername)
        verify {
            runBlocking { dataStoreManager.setUser(inputEmail, inputUsername) }
        }
    }

    @Test
    fun getEmail(): Unit = runBlocking {
        val respEmail = mockk<Flow<String>>()
        every {
            runBlocking {
                dataStoreManager.getEmail()
            }
        } returns respEmail
        repository.getEmail()
        verify {
            runBlocking { dataStoreManager.getEmail() }
        }
    }

    @Test
    fun getUsername(): Unit = runBlocking {
        val respUsername = mockk<Flow<String>>()
        every {
            runBlocking {
                dataStoreManager.getUsername()
            }
        } returns respUsername
        repository.getUsername()
        verify {
            runBlocking { dataStoreManager.getUsername() }
        }
    }

    @Test
    fun deleteUser(): Unit = runBlocking {
        every {
            runBlocking {
                dataStoreManager.deleteUser()
            }
        } returns any()
        repository.deleteUser()
        verify {
            runBlocking { dataStoreManager.deleteUser() }
        }
    }
}