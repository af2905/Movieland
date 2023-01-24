package com.github.af2905.movieland.people.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.PersonV2Item
import com.github.af2905.movieland.core.shared.GetCachedPopularPeople
import com.github.af2905.movieland.core.shared.GetPopularPeople
import com.github.af2905.movieland.core.shared.PeopleParams
import javax.inject.Inject

class PeopleViewModel @Inject constructor(
    private val getPopularPeople: GetPopularPeople,
    private val getCachedPopularPeople: GetCachedPopularPeople
) : ViewModel() {

    val container: Container<PeopleContract.State, PeopleContract.Effect> =
        Container(viewModelScope, PeopleContract.State.Init)

    init {
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
        val cachedPeople = getCachedPopularPeople(Unit).getOrDefault(emptyList())

        if (cachedPeople.isNotEmpty()) {
            container.reduce {
                PeopleContract.State.Content(list = cachedPeople.map { PersonV2Item(it) })
            }
        } else {
            container.reduce {
                PeopleContract.State.Loading()
            }
            val result = getPopularPeople(PeopleParams(forceUpdate = forceUpdate)).getOrThrow()

            container.reduce {
                PeopleContract.State.Content(list = result.map { PersonV2Item(it) })
            }
        }
    }

    fun refresh() = savedLoadData(forceUpdate = true)

    private suspend fun handleError(e: Exception) {
        val cachedMovies = getCachedPopularPeople(Unit).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                PeopleContract.State.Content(list = cachedMovies.map { PersonV2Item(it) })
            }
        } else {
            container.reduce {
                PeopleContract.State.Error(
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
            container.postEffect(PeopleContract.Effect.OpenPersonDetail(Navigate { navigator ->
                (navigator as PeopleNavigator).forwardToPersonDetailScreen(itemId)
            }))
        }
    }
}