package com.github.af2905.movieland.detail.persondetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.PersonDetailItem
import com.github.af2905.movieland.detail.usecase.params.PersonDetailParams
import com.github.af2905.movieland.detail.usecase.params.PersonMovieCreditsParams
import com.github.af2905.movieland.detail.usecase.person.GetPersonDetail
import com.github.af2905.movieland.detail.usecase.person.GetPersonMovieCredits
import kotlinx.coroutines.async
import javax.inject.Inject

class PersonDetailViewModel @Inject constructor(
    private val personId: Int,
    private val getPersonDetail: GetPersonDetail,
    private val getPersonMovieCredits: GetPersonMovieCredits
) : ViewModel() {

    val container: Container<PersonDetailContract.State, PersonDetailContract.Effect> =
        Container(viewModelScope, PersonDetailContract.State.Loading)

    val personDetailClickListener = PersonDetailItem.Listener {
        container.intent {
            container.reduce {
                if (this is PersonDetailContract.State.Content) {
                    PersonDetailContract.State.Content(
                        personDetailItem = this.personDetailItem.copy(liked = !this.personDetailItem.liked)
                    )
                } else {
                    this
                }
            }
        }
    }

    val errorItemClickListener = ErrorItem.Listener { loadData() }

    init {
        loadData()
    }

    private fun loadData() {
        container.intent {
            val castsAsync = viewModelScope.async {
                getPersonMovieCredits(PersonMovieCreditsParams(personId)).getOrNull()
            }
            val personDetailAsync = viewModelScope.async {
                getPersonDetail(PersonDetailParams(personId)).getOrThrow()
            }

            val casts = castsAsync.await().orEmpty()
            var personDetail = personDetailAsync.await()
            personDetail = personDetail.copy(personMovieCreditsCasts = casts)

            val list = mutableListOf<Model>()
            list.add(personDetail)

            casts.forEach {
                list.add(it)
            }

            container.reduce {
                PersonDetailContract.State.Content(personDetail, list)
            }
        }

/*        container.intent {
            try {
                val result = getPersonDetail(PersonDetailParams(personId)).getOrThrow()
                container.reduce {
                    val list = listOf<Model>(result)
                    PersonDetailContract.State.Content(result, list)
                }
            } catch (e: Exception) {
                //container.reduce { PersonDetailContract.State.Error(e) }
                container.intent {
                    container.postEffect(
                        PersonDetailContract.Effect.ShowFailMessage(
                            ToastMessage(ErrorHandler.handleError(e))
                        )
                    )
                }
            }
        }*/
    }
}