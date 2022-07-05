import com.aroman.gitandroid.data.web.github.GitServerResponseData
import com.aroman.gitandroid.data.web.github.GitServerResponseDataOwner
import com.aroman.gitandroid.domain.usecase.RepositoryUsecase
import com.aroman.gitandroid.ui.userDetails.UserDetailsFragment
import com.aroman.gitandroid.ui.userDetails.UserDetailsViewModel
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TestUserDetailsViewModel {
    private lateinit var viewModel: UserDetailsViewModel
    private val login = "tambosa"

    @Mock
    private lateinit var repositoryWeb: RepositoryUsecase

    @Mock
    private lateinit var repositoryLocal: RepositoryUsecase

    @Mock
    private lateinit var fragment: UserDetailsFragment

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(repositoryWeb.getListRepos(login)).thenReturn(Single.just(listOf()))
        `when`(repositoryLocal.getListRepos(login)).thenReturn(Single.just(listOf()))

        viewModel = UserDetailsViewModel(repositoryWeb, repositoryLocal)
    }

    @Test
    fun searchUserOnlineTest() {
        viewModel.getUser(true, login)
        verify(repositoryWeb, times(1)).getListRepos(login)
        verify(repositoryLocal, times(0)).getListRepos(login)
    }

    @Test
    fun searchUserOfflineTest() {
        viewModel.getUser(false, login)
        verify(repositoryLocal, times(1)).getListRepos(login)
        verify(repositoryWeb, times(0)).getListRepos(login)
    }

    @Test
    fun handleOnlineEmptyResponse() {
        val response = repositoryWeb.getListRepos(login).blockingGet()
        assertTrue(response.isEmpty())
    }

    @Test
    fun handleOfflineEmptyResponse() {
        val response = repositoryLocal.getListRepos(login).blockingGet()
        assertTrue(response.isEmpty())
    }

    @Test
    fun handleOnline_ResponseIsNotEmpty() {
        `when`(repositoryWeb.getListRepos(login)).thenReturn(
            Single.just(
                listOf(
                    GitServerResponseData(
                        "name",
                        "url",
                        GitServerResponseDataOwner("login", "avatarurl")
                    )
                )
            )
        )
        val response = repositoryWeb.getListRepos(login).blockingGet()
        assertTrue(response.isNotEmpty())
    }

    @Test
    fun handleOffline_ResponseIsNotEmpty() {
        `when`(repositoryLocal.getListRepos(login)).thenReturn(
            Single.just(
                listOf(
                    GitServerResponseData(
                        "name",
                        "url",
                        GitServerResponseDataOwner("login", "avatarurl")
                    )
                )
            )
        )
        val response = repositoryLocal.getListRepos(login).blockingGet()
        assertTrue(response.isNotEmpty())
    }
}