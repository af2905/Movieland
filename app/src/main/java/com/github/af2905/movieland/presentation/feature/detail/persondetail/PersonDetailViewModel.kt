package com.github.af2905.movieland.presentation.feature.detail.persondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.ErrorHandler
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.domain.usecase.params.PersonDetailParams
import com.github.af2905.movieland.domain.usecase.people.GetPersonDetail
import javax.inject.Inject

class PersonDetailViewModel @Inject constructor(
    args: PersonDetailFragmentArgs,
    private val getPersonDetail: GetPersonDetail
) : ViewModel() {

    private val personId = args.personId

    val container: Container<PersonDetailContract.State, PersonDetailContract.Effect> =
        Container(viewModelScope, PersonDetailContract.State.Loading)

    val personDetailClickListener = {
        container.intent {
            container.reduce {
                if (this is PersonDetailContract.State.Content) {
                    PersonDetailContract.State.Content(
                        personItem = this.personItem.copy(liked = !this.personItem.liked)
                    )
                } else {
                    this
                }
            }
        }
    }

    val errorClickListener = { loadData() }

    init {
        loadData()
    }

    private fun loadData() {
        container.intent {
            try {
                val result = getPersonDetail(PersonDetailParams(personId)).getOrThrow()
                container.reduce {
                    PersonDetailContract.State.Content(result)
                }
            } catch (e: Exception) {
                container.reduce { PersonDetailContract.State.Error(e) }
                container.intent {
                    container.postEffect(
                        PersonDetailContract.Effect.ShowFailMessage(
                            ToastMessage(ErrorHandler.handleError(e))
                        )
                    )
                }
            }
        }
    }
}