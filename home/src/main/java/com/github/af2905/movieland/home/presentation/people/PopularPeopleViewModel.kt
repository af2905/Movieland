package com.github.af2905.movieland.home.presentation.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.PersonV2Item
import com.github.af2905.movieland.home.domain.params.CachedPeopleParams
import com.github.af2905.movieland.home.domain.params.PeopleParams
import com.github.af2905.movieland.home.domain.usecase.GetCachedPopularPeople
import com.github.af2905.movieland.home.domain.usecase.GetPopularPeople
import com.github.af2905.movieland.home.repository.HomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularPeopleViewModel @Inject constructor(
    private val getPopularPeople: GetPopularPeople,
    private val getCachedPopularPeople: GetCachedPopularPeople,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<PopularPeopleContract.State, PopularPeopleContract.Effect> =
        Container(viewModelScope, PopularPeopleContract.State.Init)

    init {
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
        savedLoadData()
    }

    private fun savedLoadData(forceUpdate: Boolean = false) {
        container.intent {
            try {
                loadData(forceUpdate = forceUpdate)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private suspend fun loadData(forceUpdate: Boolean) {
        val cachedPeople = getCachedPopularPeople(CachedPeopleParams).getOrDefault(emptyList())

        if (cachedPeople.isNotEmpty()) {
            container.reduce {
                PopularPeopleContract.State.Content(list = cachedPeople.map { PersonV2Item(it) })
            }
        } else {
            container.reduce {
                PopularPeopleContract.State.Loading()
            }
            val result = getPopularPeople(PeopleParams(forceUpdate = forceUpdate)).getOrThrow()

            container.reduce {
                PopularPeopleContract.State.Content(list = result.map { PersonV2Item(it) })
            }
        }
    }

    private fun refresh() = savedLoadData(forceUpdate = true)

    private suspend fun handleError(e: Exception) {
        val cachedMovies = getCachedPopularPeople(CachedPeopleParams).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                PopularPeopleContract.State.Content(list = cachedMovies.map { PersonV2Item(it) })
            }
        } else {
            container.reduce {
                PopularPeopleContract.State.Error(
                    list = listOf(
                        ErrorItem(
                            errorMessage = e.message.orEmpty(),
                            errorButtonVisible = false
                        )
                    ),
                    e = e
                )
            }
        }
    }

    fun openDetail(itemId: Int) {
        container.intent {
            container.postEffect(PopularPeopleContract.Effect.OpenPersonDetail(Navigate { navigator ->

            }))
        }
    }
}